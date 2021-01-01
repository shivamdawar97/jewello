package com.dawar.jewellerybilling.bills

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.database.entities.Bill
import com.dawar.jewellerybilling.print.printBill.PrintBillActivity
import java.util.*

class BillsAdapter(private val bills:List<Bill>) : RecyclerView.Adapter<BillsAdapter.ViewHolder>()  {
     inner class ViewHolder(private val mView: View): RecyclerView.ViewHolder(mView) {
         private val billIdView = mView.findViewById<TextView>(R.id.bill_no)
         private val amount = mView.findViewById<TextView>(R.id.field_received)
         private val customerNameView = mView.findViewById<TextView>(R.id.customer_name)
         private val dateView = mView.findViewById<TextView>(R.id.date).also {
             mView.findViewById<LinearLayout>(R.id.customer_layout).visibility = View.VISIBLE
         }
         fun setData(bill: Bill) = with(bill){
             billIdView.text = billId.toString()
             amount.text = totalAmount.toString()
             customerNameView.text = customerName
             dateView.text = Date(date).toString()
             mView.setOnClickListener { val context = it.context
                 context.startActivity(Intent(context, PrintBillActivity::class.java).putExtra("billId",billId))
             }
         }

     }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(bills[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_record,parent,false))

    override fun getItemCount() = bills.size
}