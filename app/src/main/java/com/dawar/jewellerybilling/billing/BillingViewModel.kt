package com.dawar.jewellerybilling.billing

import androidx.datastore.core.DataStore
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.datastore.preferences.core.Preferences
import com.dawar.jewellerybilling.Utils.GOLD_RATE
import com.dawar.jewellerybilling.Utils.SILVER_RATE
import com.dawar.jewellerybilling.Utils.getValueFlow
import com.dawar.jewellerybilling.Utils.setValue
import com.dawar.jewellerybilling.database.entities.Customer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BillingViewModel @ViewModelInject constructor(
    private val repository: BillingRepository,
    private val dataStore: DataStore<Preferences>,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _editRateEnabled = MutableLiveData<Boolean>().apply { value = false }
    val goldRate = MutableLiveData<Float>().apply { value = 0f }
    private val _silverRate = MutableLiveData<Float>().apply { value = 0f }

    private var totalGoldWeight =  0f
    private var totalSilverWeight =  0f
    private var totalLabour = 0

    val customer = MutableLiveData<Customer>()
    val items = repository.getAllItems()
    val customers = repository.getAllCustomers()
    val lastBillNo = MutableLiveData<Int>()
    val editRateEnabled: LiveData<Boolean>
        get() = _editRateEnabled
    val customerName = MutableLiveData<String>()
    val totalAmount = MutableLiveData<Int>().apply { value = 0 }

    val validUser = MediatorLiveData<Boolean>().apply {
        addSource(customerName) {
            customer.value = customers.value?.find { it.name == customerName.value}
            value = customer.value != null
        }
    }
    val received = MutableLiveData<String>().apply { value = "0" }
    val balance = MediatorLiveData<Int>().apply {
        addSource(received){
            value = totalAmount.value?:0 - it.toInt()
        }
        addSource(totalAmount){
            value = it - if(received.value!="") received.value!!.toInt() else 0
        }
    }

    init {
        initializeDataSource()
    }

    private fun initializeDataSource() = viewModelScope.launch {
        lastBillNo.value = repository.getLastBillId() + 1
        dataStore.getValueFlow(GOLD_RATE,0f).collect { goldRate.value = it }
        dataStore.getValueFlow(SILVER_RATE,0f).collect { _silverRate.value = it }

    }

    fun editRateEnabled() {
        _editRateEnabled.value = true
    }

    fun applyRates(goldRate: String?, silverRate: String?) = viewModelScope.launch{
        this@BillingViewModel.goldRate.value = (goldRate ?: "0").toFloat()
        _silverRate.value = (silverRate ?: "0").toFloat()
        _editRateEnabled.value = false
        dataStore.setValue(GOLD_RATE, this@BillingViewModel.goldRate.value!!)
        dataStore.setValue(SILVER_RATE,_silverRate.value!!)
        calculate()
    }

    fun reset(){
        customerName.value = ""
        totalAmount.value = 0
        totalGoldWeight = 0f
        totalSilverWeight = 0f
        totalLabour = 0
        received.value = "0"
    }

    fun updateWeights(goldWeight: Float, silverWeight: Float, labour: Int){
        totalGoldWeight = goldWeight ; totalSilverWeight = silverWeight ; totalLabour = labour
        calculate()
    }

    private fun calculate() {
        totalAmount.value =
            (totalGoldWeight * goldRate.value!! + totalSilverWeight * _silverRate.value!!).toInt() + totalLabour
    }
}