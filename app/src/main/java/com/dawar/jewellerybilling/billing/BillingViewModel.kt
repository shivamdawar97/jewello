package com.dawar.jewellerybilling.billing

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.database.entities.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BillingViewModel @ViewModelInject constructor(
    private val repository: BillingRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _editRateEnabled = MutableLiveData<Boolean>().apply { value = false }
    private val _goldRate = MutableLiveData<Float>().apply { value = 0f }
    private val _silverRate = MutableLiveData<Float>().apply { value = 0f }

    val customer = MutableLiveData<Customer>()
    val items = repository.getAllItems()
    val customers = repository.getAllCustomers()
    val lastBillNo = MutableLiveData<Int>()
    val editRateEnabled: LiveData<Boolean>
        get() = _editRateEnabled
    val customerName = MutableLiveData<String>()
    val totalGoldWeight = MutableLiveData<Float>().apply { value = 0f }
    val totalSilverWeight = MutableLiveData<Float>().apply { value = 0f }
    val totalLabour = MutableLiveData<Int>().apply { value = 0 }
    val received = MutableLiveData<Int>().apply { value = 0 }
    val balance = MediatorLiveData<Int>().apply {
        addSource(received){
            value = totalAmount.value!! - it
        }
    }

    val totalAmount = MediatorLiveData<Int>().apply {
        addSource(totalGoldWeight){ value = calculateAmount() }
        addSource(totalSilverWeight){ value = calculateAmount() }
        addSource(totalLabour){ value = calculateAmount() }
    }
    val validUser = MediatorLiveData<Boolean>().apply {
        addSource(customerName) {
            customer.value = customers.value?.find { it.name == customerName.value}
            value = customer.value != null
        }
    }

    init {
        initializeDataSource()
    }

    private fun initializeDataSource() = viewModelScope.launch {
        lastBillNo.value = repository.getLastBillId() + 1
    }

    fun editRateEnabled() {
        _editRateEnabled.value = true
    }

    fun applyRates(goldRate: String?, silverRate: String?) {
        _goldRate.value = (goldRate ?: "0").toFloat()
        _silverRate.value = (silverRate ?: "0").toFloat()
        _editRateEnabled.value = false
    }

    private fun calculateAmount() =
        (totalGoldWeight.value!! * _goldRate.value!! + totalSilverWeight.value!! * _silverRate.value!!).toInt() + totalLabour.value!!
}