package com.dawar.jewellerybilling.print.printBill

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.databinding.ActivityPrintBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrintBillActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPrintBinding
    private val viewModel by viewModels<PrintBillViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_print)
        binding.lifecycleOwner = this
        val billId = intent.getLongExtra("billID",0L)
        viewModel.getBillAndCustomer(billId){ bill, customer ->
            binding.bill = bill ; binding.customer = customer
            addItems()
        }
    }

    private fun addItems() {
        for(item in binding.bill!!.items){
            val view = layoutInflater.inflate(R.layout.card_bill_item,null)
            view.findViewById<EditText>(R.id.item_weight).setText(item.weight.toString())
            view.findViewById<EditText>(R.id.polish_charge).setText(item.item.polishCharge.toString())
            view.findViewById<EditText>(R.id.labour).setText(item.item.labour.toString())
            view.findViewById<AppCompatImageView>(R.id.edit).visibility = View.GONE
            view.findViewById<AppCompatImageView>(R.id.remove).visibility = View.GONE
            binding.itemsContainer.addView(view)
        }
    }

    fun goBack(v: View) = finish()
}