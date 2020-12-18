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

class BillingViewModel @ViewModelInject constructor (
    private val repository: BillingRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private val _editRateEnabled = MutableLiveData<Boolean>().also { it.value = false }
    private val _goldRate = MutableLiveData<Float>().also { it.value = 0f }
    private val _silverRate = MutableLiveData<Float>().also { it.value = 0f }
    lateinit var items: LiveData<List<Item>>
    val customers = repository.getAllCustomers()

    val lastBillNo = MutableLiveData<Int>()

    val editRateEnabled: LiveData<Boolean>
        get() = _editRateEnabled

    init { initializeDataSource() }

    private fun initializeDataSource() = viewModelScope.launch {
        items = repository.getAllItems()
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
}