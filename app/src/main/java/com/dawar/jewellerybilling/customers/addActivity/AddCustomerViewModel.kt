package com.dawar.jewellerybilling.customers.addActivity

import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.entities.Customer
import kotlinx.coroutines.launch

class AddCustomerViewModel @ViewModelInject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    val name = MutableLiveData<String>()
    val number = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val balance = MutableLiveData<String>().also { it.value = "0" }

    val valid = MediatorLiveData<Boolean>().apply {
        addSource(name){ isFormValid() }
        addSource(number){ isFormValid() }
        addSource(email){ isFormValid() }
        addSource(address){ isFormValid() }
        addSource(balance){ isFormValid() }
    }

    private fun isFormValid() {
        valid.value = name.value != null && name.value != "" &&
                number.value != null && number.value!!.length == 10 &&
                (Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches() || email.value!! =="") &&
                address.value != null && address.value != "" &&
                balance.value != ""
    }

    fun addCustomer() = viewModelScope.launch {
        val newCustomer = Customer(0,name.value!!,number.value!!,email.value!!,address.value!!,balance.value!!.toInt())
        repository.addCustomer(newCustomer)
        clearForm()
    }

    private fun clearForm() {
        name.value = ""; number.value = ""; email.value = ""; address.value = ""; balance.value = "0"

    }

}