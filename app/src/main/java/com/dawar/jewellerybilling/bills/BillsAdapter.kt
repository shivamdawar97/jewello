package com.dawar.jewellerybilling.bills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.database.entities.Bill

class BillsAdapter : PagedListAdapter<Bill,BillsAdapter.ViewHolder>(DIFF_CALLBACK)  {
     inner class ViewHolder(mView: View): RecyclerView.ViewHolder(mView)

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Bill>() {
            override fun areItemsTheSame(oldItem: Bill, newItem: Bill) = oldItem.billId == newItem.billId

            override fun areContentsTheSame(oldItem: Bill, newItem: Bill) = oldItem == newItem

        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_record,parent,false))

}