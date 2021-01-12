package com.dawar.jewellerybilling.customers.addActivity

import android.util.Log
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.rx.RxBus
import com.dawar.jewellerybilling.rx.RxEvent
import kotlinx.coroutines.launch

class AddCustomerViewModel @ViewModelInject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    val name = MutableLiveData<String>().also { it.value ="" }
    val number = MutableLiveData<String>().also { it.value ="" }
    val email = MutableLiveData<String>().also { it.value ="" }
    val address = MutableLiveData<String>().also { it.value ="" }
    val balance = MutableLiveData<String>().also { it.value = "0" }

    val valid = MediatorLiveData<Boolean>().apply {
        addSource(name){ isFormValid() }
        addSource(number){ isFormValid() }
        addSource(email){ isFormValid() }
        addSource(address){ isFormValid() }
        addSource(balance){ isFormValid() }
    }

    private fun isFormValid() {
        valid.value = name.value != "" && number.value?.length == 10 &&
                (email.value =="" || Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()) &&
                address.value != "" && balance.value != ""
    }

    fun addCustomer() = viewModelScope.launch {
        val newCustomer = Customer(0,name.value!!,number.value!!,email.value!!,address.value!!,balance.value!!.toInt())
        repository.addCustomer(newCustomer)
        RxBus.publish(RxEvent.ItemAdded())
    }

}