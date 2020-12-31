package com.dawar.jewellerybilling.customers.updateActivity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.database.entities.Record
import com.dawar.jewellerybilling.print.PrintActivity
import java.util.*

class RecordsRecyclerViewAdapter(private val record:List<Record>) :
    RecyclerView.Adapter<RecordsRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(private val view: View):RecyclerView.ViewHolder(view) {

        private val label = view.findViewById<TextView>(R.id.field_label)
        private val received = view.findViewById<TextView>(R.id.field_received)
        private val billNo = view.findViewById<TextView>(R.id.bill_no)
        private val dateField = view.findViewById<TextView>(R.id.date)
        private val billLayout = view.findViewById<LinearLayout>(R.id.bill_layout)

        fun populate(record: Record) = with(record){
            received.text = amount.toString()
            dateField.text = Date(date).toString()
            if(billId==0L){
                billLayout.visibility = View.GONE
                label.text = "Amount Received"
                view.setOnClickListener(null)
            }else {
                billLayout.visibility = View.VISIBLE
                label.text = "Amount"
                billNo.text = billId.toString()
                view.setOnClickListener {
                    it.context.startActivity(Intent(it.context, PrintActivity::class.java).putExtra("billId",billId))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_record,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.populate(record[i])
    }

    override fun getItemCount() = record.size

}