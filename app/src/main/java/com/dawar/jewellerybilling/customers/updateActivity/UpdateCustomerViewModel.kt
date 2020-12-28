package com.dawar.jewellerybilling.customers.updateActivity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.entities.Customer
import kotlinx.coroutines.launch

class UpdateCustomerViewModel @ViewModelInject constructor(
    private val repository: CustomerRepository
):ViewModel() {

    val isDetailsTabSelected = MutableLiveData<Boolean>().apply { value = true }
    val isInEditMode = MutableLiveData<Boolean>().apply { value = false }
    val valid = MutableLiveData<Boolean>().apply { value = true }

    fun saveCustomer(customer:Customer) = viewModelScope.launch{
        isInEditMode.value = false
        repository.saveCustomer(customer)
    }

}