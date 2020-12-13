package com.dawar.jewellerybilling.customers.listActivity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.customers.updateActivity.UpdateCustomerActivity
import com.dawar.jewellerybilling.database.entities.Customer

class CustomersRecyclerViewAdapter(private val customers: List<Customer>) :
    RecyclerView.Adapter<CustomersRecyclerViewAdapter.ViewHolder>(), Filterable
{

    private var filteredCustomers = customers

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val idView = view.findViewById<TextView>(R.id.customer_id)!!
        private val nameView = view.findViewById<TextView>(R.id.customer_name)!!
        private val numberView = view.findViewById<TextView>(R.id.customer_number)!!
        private val balanceView = view.findViewById<TextView>(R.id.balance)!!
        fun populateCard(customer:Customer) = with(customer){
            idView.text = customerId.toString()
            nameView.text = name
            numberView.text = phoneNumber
            balanceView.text = balance.toString()
            view.setOnClickListener {
                val intent = Intent(view.context, UpdateCustomerActivity::class.java)
                intent.putExtra("id",customerId)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_customer,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateCard(filteredCustomers[position])
    }


    override fun getItemCount() = filteredCustomers.size

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val cs = constraint.toString()
            filteredCustomers = if(cs.isEmpty()) customers else {
                val filteredList = ArrayList<Customer>()
                customers.forEach {
                    if(it.name.toLowerCase().contains(cs.toLowerCase()) || it.phoneNumber.contains(cs))
                        filteredList.add(it)
                }
                filteredList
            }
            return FilterResults().also { it.values = filteredCustomers }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if(results?.values != null){
                filteredCustomers = results.values as List<Customer>
                notifyDataSetChanged()
            }
        }

    }

}
