package com.dawar.jewellerybilling.customers.updateActivity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.marginLeft
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.getTextToInt
import com.dawar.jewellerybilling.Utils.onTabSelected
import com.dawar.jewellerybilling.Utils.onTextChanged
import com.dawar.jewellerybilling.customers.addActivity.AddCustomerViewModel
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.databinding.ActivityUpdateCustomerBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCustomerBinding
    private val viewModel by viewModels<UpdateCustomerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_customer)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.customer = intent.getSerializableExtra("customer") as Customer

        setUpRecyclerView()
        setUpListeners()
    }

    private fun setUpRecyclerView() {
        binding.recordsRecycler.layoutManager = LinearLayoutManager(this)
        viewModel.getRecords(binding.customer!!.customerId)
        viewModel.record.observeForever {
            binding.recordsRecycler.adapter = RecordsRecyclerViewAdapter(it)
        }
    }

    private fun setUpListeners() {
        binding.tabLayout.onTabSelected {
            viewModel.isDetailsTabSelected.value = it.position == 0
        }

        binding.customerNumber.onTextChanged {
            viewModel.valid.value = isValid(it.toString(),binding.customerAddress.text.toString())
        }

        binding.customerAddress.onTextChanged {
            viewModel.valid.value = isValid(binding.customerNumber.text.toString(),it.toString())
        }
    }

    private fun isValid(phn:String,address:String) =
        phn.isNotBlank() && phn.length == 10 && address.isNotBlank()

    fun edit(v: View) {
        viewModel.isInEditMode.value = true
    }

    fun amountReceived(v:View){
        val editText = EditText(this)
        editText.setPadding(10,5,5,10)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        AlertDialog.Builder(this)
            .setTitle("Enter Amount")
            .setView(editText)
            .setPositiveButton("Update"){ d,i ->
                d.dismiss()
                val amount = editText.getTextToInt()
                if(amount!=0) updateBalance(amount)
            }
            .setNegativeButton("Cancel"){ d,i -> d.dismiss()}
            .create().show()
    }

    private fun updateBalance(amount: Int) {
        binding.customer!!.balance -= amount
        binding.balance.text = binding.customer!!.balance.toString()
        viewModel.saveCustomerAndAddInRecord(binding.customer!!,amount){
            record,customer ->
            val i = Intent(this,AmountReceivedPrintActivity::class.java)
            i.putExtra("record",record).putExtra("customer",customer)

        }
    }

    fun goBack(v: View) = finish()
}