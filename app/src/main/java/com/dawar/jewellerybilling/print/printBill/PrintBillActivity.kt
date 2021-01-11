package com.dawar.jewellerybilling.print.printBill

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.getTextToInt
import com.dawar.jewellerybilling.Utils.getTextToLong
import com.dawar.jewellerybilling.Utils.onTextChanged
import com.dawar.jewellerybilling.databinding.ActivityPrintBinding
import com.dawar.jewellerybilling.print.JewelloBluetoothSocket
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PrintBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrintBinding
    private val viewModel by viewModels<PrintBillViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_print)
        binding.lifecycleOwner = this
        intent.getLongExtra("billId", 0L).let {
            viewModel.billId.value = it
            binding.billIdField.setText(it.toString())
        }

        viewModel.bill.observeForever {
            binding.bill = it;addItems()
        }
        binding.billIdField.onTextChanged {
            viewModel.billId.value = if(it.isNotBlank()) it.toString().toLong() else 0L
        }
        binding.isConnected.text = JewelloBluetoothSocket.isConnected().toString()
        binding.viewModel = viewModel
    }

    private fun addItems() = binding.bill?.let {
        binding.itemsContainer.removeAllViews()
        for (item in binding.bill!!.items) {
            val view = layoutInflater.inflate(R.layout.card_bill_item,null)
            view.findViewById<TextView>(R.id.item_name).text = item.item.name
            view.findViewById<EditText>(R.id.item_weight).setText(item.weight.toString())
            view.findViewById<EditText>(R.id.polish_charge)
                .setText(item.item.polishCharge.toString())
            view.findViewById<EditText>(R.id.labour).setText(item.item.labour.toString())
            view.findViewById<AppCompatImageView>(R.id.edit).visibility = View.GONE
            view.findViewById<AppCompatImageView>(R.id.remove).visibility = View.GONE
            binding.itemsContainer.addView(view)
        }
    }

    fun goBack(v: View) = finish()

    fun nextBill(v:View) {
        binding.billIdField.setText((binding.billIdField.getTextToLong() + 1).toString())
    }

    fun previousBill(v:View) = (binding.billIdField.getTextToLong() - 1).let{
        binding.billIdField.setText(if(it<0) "0" else it.toString())
    }

}