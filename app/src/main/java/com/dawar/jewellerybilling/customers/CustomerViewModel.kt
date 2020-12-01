package com.dawar.jewellerybilling.customers

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomerViewModel : ViewModel() {

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
                email.value != null && Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches() &&
                address.value != null && address.value != "" &&
                balance.value != ""
    }

    fun addCustomer() {
        Log.i("ENABLED", "Clicked")
    }
}