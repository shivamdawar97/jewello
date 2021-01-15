package com.dawar.jewellerybilling.customers.updateActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.getFormattedDate
import com.dawar.jewellerybilling.database.daos.CustomerDao
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.database.entities.Record
import com.dawar.jewellerybilling.databinding.ActivityAmountReceivedPrintBinding
import com.dawar.jewellerybilling.print.JewelloBluetoothSocket
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AmountReceivedPrintActivity : AppCompatActivity() {

    private lateinit var biniding: ActivityAmountReceivedPrintBinding
    @Inject lateinit var customerDao: CustomerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biniding = DataBindingUtil.setContentView(this, R.layout.activity_amount_received_print)

        biniding.record = intent.getSerializableExtra("record") as Record
        intent.getSerializableExtra("customer").let {
            if(it!=null) biniding.customer = it as Customer
            else getAsyncCustomer()
        }


    }

    private fun getAsyncCustomer() = lifecycleScope.launch{
        biniding.customer = customerDao.get(biniding.record!!.customerId)
    }

    fun goBack(v: View) = finish()

    fun print(view: View) = with(biniding) {
        val sb = StringBuilder()
        sb.append("Amount Received\n")
            .append("${customer!!.name}\n")
            .append(Date(record!!.date).getFormattedDate())
            .append("Amount Received :${record!!.amount}")
            .append("Total due balance : ${customer!!.balance}")
        JewelloBluetoothSocket.printData(sb.toString())
    }
}
