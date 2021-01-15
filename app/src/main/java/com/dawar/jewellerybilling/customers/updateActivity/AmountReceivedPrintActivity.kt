package com.dawar.jewellerybilling.customers.updateActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.getFormattedDate
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.database.entities.Record
import com.dawar.jewellerybilling.databinding.ActivityAmountReceivedPrintBinding
import com.dawar.jewellerybilling.print.JewelloBluetoothSocket
import java.util.*

class AmountReceivedPrintActivity : AppCompatActivity() {
    private lateinit var biniding: ActivityAmountReceivedPrintBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biniding = DataBindingUtil.setContentView(this, R.layout.activity_amount_received_print)

        biniding.record = intent.getSerializableExtra("record") as Record
        biniding.customer = intent.getSerializableExtra("customer") as Customer

    }

    fun goBack(v: View) = finish()

    fun print(view: View) = with(biniding) {
        val sb = StringBuilder()
        sb.append("Amount Received\n")
            .append("${customer.name}\n")
            .append(Date(record.date).getFormattedDate())
            .append("Amount Received :${record.amount}")
            .append("Total due balance : ${customer.balance}")
        JewelloBluetoothSocket.printData(sb.toString())
    }
}
