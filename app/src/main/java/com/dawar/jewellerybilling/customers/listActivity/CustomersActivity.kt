package com.dawar.jewellerybilling.customers.listActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.customers.addActivity.AddCustomerActivity
import com.dawar.jewellerybilling.databinding.ActivityCustomersBinding
import com.dawar.jewellerybilling.Utils.onTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomersActivity : AppCompatActivity() {

    private val viewModel: CustomersViewModel by viewModels()
    private lateinit var binding : ActivityCustomersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customers)
        binding.customersRecycler.layoutManager = LinearLayoutManager(this)
        viewModel.customers?.observe(this, {
            binding.customersRecycler.adapter = CustomersRecyclerViewAdapter(it)
        })
        binding.searchField.onTextChanged {
            (binding.customersRecycler.adapter as CustomersRecyclerViewAdapter).filter.filter(it)
        }
    }

    fun goBack(v: View) = finish()

    fun addCustomer(v:View) =  startActivity(Intent(this, AddCustomerActivity::class.java))



}