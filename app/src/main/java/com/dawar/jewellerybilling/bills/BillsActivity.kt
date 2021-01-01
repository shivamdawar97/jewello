package com.dawar.jewellerybilling.bills

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.databinding.ActivityBillsBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BillsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBillsBinding
    private val viewModel by viewModels<BillsActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bills)
        binding.lifecycleOwner = this
        binding.billsRecycler.layoutManager = LinearLayoutManager(this)

        viewModel.bills.observeForever {
            binding.billsRecycler.adapter = BillsAdapter(it)
        }
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select a date").build()
        datePicker.addOnPositiveButtonClickListener {
            //binding.datePicker.setText(datePicker.headerText)
            viewModel.date.value = Date(it)
        }
        binding.datePicker.setOnClickListener {
            datePicker.show(supportFragmentManager,"DATE_PICKER")
        }
    }

    fun goBack(v: View) = finish()
}