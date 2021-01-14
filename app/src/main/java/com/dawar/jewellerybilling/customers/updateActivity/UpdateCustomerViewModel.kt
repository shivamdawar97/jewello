package com.dawar.jewellerybilling.customers.updateActivity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.database.entities.Record
import com.dawar.jewellerybilling.records.RecordRepository
import kotlinx.coroutines.launch
import java.util.*

class UpdateCustomerViewModel @ViewModelInject constructor(
    private val repository: CustomerRepository,
    private val recordsRepository: RecordRepository
):ViewModel() {

    val isDetailsTabSelected = MutableLiveData<Boolean>().apply { value = true }
    val isInEditMode = MutableLiveData<Boolean>().apply { value = false }
    val valid = MutableLiveData<Boolean>().apply { value = true }
    lateinit var record : LiveData<List<Record>>

    fun saveCustomer(customer:Customer) = viewModelScope.launch{
        isInEditMode.value = false
        repository.saveCustomer(customer)
    }

    fun getRecords(id:Long) = viewModelScope.launch{
        record = recordsRepository.getRecordByCustomerId(id)
    }

    fun saveCustomerAndAddInRecord(customer:Customer,amount:Int,callback:(Record,Customer)->Unit) = viewModelScope.launch{
        repository.saveCustomer(customer)
        val newRecord = Record(date = Date().time,customerId = customer.customerId,amount = amount)
        newRecord.recordId = recordsRepository.saveRecord(newRecord)
        callback(newRecord,customer)
    }

}