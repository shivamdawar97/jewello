package com.dawar.jewellerybilling.customers.updateActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.onTabSelected
import com.dawar.jewellerybilling.Utils.onTextChanged
import com.dawar.jewellerybilling.customers.addActivity.AddCustomerViewModel
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.databinding.ActivityUpdateCustomerBinding
import com.google.android.material.tabs.TabLayout

class UpdateCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCustomerBinding
    private val viewModel by viewModels<UpdateCustomerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_customer)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.customer = intent.getSerializableExtra("customer") as Customer

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.tabLayout.onTabSelected {
            viewModel.isDetailsTabSelected.value = it.position == 0
        }

        binding.customerNumber.onTextChanged {
            viewModel.valid.value =
                it.isNotEmpty() && binding.customerAddress.text.toString().isNotBlank()
        }

        binding.customerAddress.onTextChanged {
            viewModel.valid.value =
                it.isNotEmpty() && binding.customerNumber.text.toString().isNotBlank()
        }
    }

    fun edit(v: View) {
        viewModel.isInEditMode.value = true
    }

    fun goBack(v: View) = finish()
}