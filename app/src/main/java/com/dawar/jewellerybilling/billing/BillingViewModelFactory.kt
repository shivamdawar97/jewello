package com.dawar.jewellerybilling.billing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BillingViewModelFactory(private val isEditRateEnabled:Boolean=false) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BillingViewModel::class.java))
        return BillingViewModel(isEditRateEnabled) as T
        throw IllegalArgumentException("Unknown viewModel class")
    }
}