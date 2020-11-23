package com.dawar.jewellerybilling.billing

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BillingViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BillingViewModel::class.java))
            return BillingViewModel(context) as T
        throw IllegalArgumentException("Unknown viewModel class")
    }
}