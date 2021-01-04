package com.dawar.jewellerybilling.billing

import androidx.datastore.core.DataStore
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.datastore.preferences.core.Preferences
import com.dawar.jewellerybilling.Utils.GOLD_RATE
import com.dawar.jewellerybilling.Utils.SILVER_RATE
import com.dawar.jewellerybilling.Utils.getRateValuesFlow
import com.dawar.jewellerybilling.Utils.setRateValues
import com.dawar.jewellerybilling.database.entities.Bill
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.database.entities.Item
import com.dawar.jewellerybilling.database.entities.Record
import com.dawar.jewellerybilling.print.JewelloBluetoothSocket
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class BillingViewModel @ViewModelInject constructor(
    private val repository: BillingRepository,
    private val dataStore: DataStore<Preferences>,
    private val bluetoothSocket: JewelloBluetoothSocket
) : ViewModel() {

    val goldRate: LiveData<Int>
        get() = _goldRate
    val silverRate: LiveData<Int>
        get() = _silverRate

    private val _editRateEnabled = MutableLiveData<Boolean>().apply { value = false }
    private val _goldRate = MutableLiveData<Int>().apply { value = 0 }
    private val _silverRate = MutableLiveData<Int>().apply { value = 0 }

    private var totalGoldWeight = 0f
    private var totalSilverWeight = 0f
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
            customer.value = customers.value?.find { it.name == customerName.value }
            value = customer.value != null
        }
    }
    val received = MutableLiveData<String>().apply { value = "0" }
    val balance = MediatorLiveData<Int>().apply {
        addSource(received) {
            value = (totalAmount.value ?: 0) - if (it != "") it.toInt() else 0
        }
        addSource(totalAmount) {
            value = it - if (received.value != "") received.value!!.toInt() else 0
        }
    }

    init {
        initializeDataSource()
    }

    private fun initializeDataSource() = viewModelScope.launch {
        bluetoothSocket.findDeviceAndConnect()
        lastBillNo.value = repository.getLastBillId() + 1
        dataStore.getRateValuesFlow(arrayOf(GOLD_RATE, SILVER_RATE), arrayOf(0,0)).collect {
            _goldRate.value = it.goldRate
            _silverRate.value = it.silverRate
        }
    }

    fun editRateEnabled() {
        _editRateEnabled.value = true
    }

    fun applyRates(goldRate: String?, silverRate: String?) = viewModelScope.launch {
        _editRateEnabled.value = false
        dataStore.setRateValues(arrayOf(GOLD_RATE, SILVER_RATE) ,
            arrayOf((goldRate ?: "0").toInt(),(silverRate ?: "0").toInt()))
        calculate()
    }

    fun reset() {
        customerName.value = ""
        totalAmount.value = 0
        totalGoldWeight = 0f
        totalSilverWeight = 0f
        totalLabour = 0
        received.value = "0"
    }

    fun updateWeights(goldWeight: Float, silverWeight: Float, labour: Int) {
        totalGoldWeight = goldWeight; totalSilverWeight = silverWeight; totalLabour = labour
        calculate()
    }

    private fun calculate() {
        totalAmount.value =
            (totalGoldWeight * _goldRate.value!! + totalSilverWeight * _silverRate.value!!).toInt() + totalLabour
    }

    fun saveBill(billItemList: ArrayList<BillItem>,listener:(Long)->Unit) = viewModelScope.launch{
        val newBill = generateBill(billItemList)
        customer.value!!.balance += newBill.balanceAmount
        repository.saveCustomer(customer.value!!)
        val newRecord = Record(
            recordId = 0,
            date = newBill.date,
            amount = newBill.totalAmount,
            billId = repository.saveBill(newBill),
            customerId = newBill.customerId
        )
        repository.saveRecord(newRecord)
        lastBillNo.value = repository.getLastBillId() + 1
        listener(newRecord.billId)
    }

    fun sendBillToPending(billItemList: ArrayList<BillItem>) = viewModelScope.launch{
        val pendingBill = generateBill(billItemList)
        repository.savePending(pendingBill)
    }

    private fun generateBill(billItemList: ArrayList<BillItem>) = Bill(
            goldRate = _goldRate.value!!,
            silverRate = _silverRate.value!!,
            items = billItemList,
            customerId = customer.value!!.customerId,
            customerName = customer.value!!.name,
            date = Date().time,
            totalAmount = totalAmount.value!!,
            amountReceived = received.value!!.toInt(),
            balanceAmount = balance.value!!
    )
}