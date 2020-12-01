package com.dawar.jewellerybilling.customers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseMethod
import androidx.lifecycle.ViewModelProvider
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.databinding.ActivityAddCustomerBinding
import com.dawar.jewellerybilling.databinding.ActivityBillingBinding

class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer)
        binding.lifecycleOwner = this
        binding.viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)

    }

}