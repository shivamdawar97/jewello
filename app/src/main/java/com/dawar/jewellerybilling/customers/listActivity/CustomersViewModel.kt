package com.dawar.jewellerybilling.customers.listActivity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.entities.Customer
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomersViewModel @ViewModelInject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    var customers: LiveData<List<Customer>>? = null

    init {
        viewModelScope.launch {
            customers = repository.getAllCustomers()
        }
    }

}