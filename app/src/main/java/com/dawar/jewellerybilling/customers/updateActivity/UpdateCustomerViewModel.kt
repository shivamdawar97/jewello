package com.dawar.jewellerybilling.customers.updateActivity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.database.entities.Record
import com.dawar.jewellerybilling.records.RecordRepository
import kotlinx.coroutines.launch

class UpdateCustomerViewModel @ViewModelInject constructor(
    private val repository: CustomerRepository,
    private val recordsRepository: RecordRepository
):ViewModel() {

    val isDetailsTabSelected = MutableLiveData<Boolean>().apply { value = true }
    val isInEditMode = MutableLiveData<Boolean>().apply { value = false }
    val valid = MutableLiveData<Boolean>().apply { value = true }
    val record = MutableLiveData<List<Record>>()


    fun saveCustomer(customer:Customer) = viewModelScope.launch{
        isInEditMode.value = false
        repository.saveCustomer(customer)
    }

    fun getRecords(id:Long) = viewModelScope.launch{
        record.value = recordsRepository.getRecordByCustomerId(id)
    }

}