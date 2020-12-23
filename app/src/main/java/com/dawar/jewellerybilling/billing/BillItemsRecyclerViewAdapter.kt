package com.dawar.jewellerybilling.billing

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.database.entities.Item

class BillItemsRecyclerViewAdapter(private val list:ArrayList<Item>):RecyclerView.Adapter<BillItemsRecyclerViewAdapter.ViewHolder>() {

    fun addItem(newItem:Item) = list.add(newItem)

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val itemName = view.findViewById<TextView>(R.id.item_name)
        private val weightField = view.findViewById<EditText>(R.id.item_weight)
        private val polishField = view.findViewById<EditText>(R.id.polish_charge)
        private val labourField = view.findViewById<EditText>(R.id.labour)
        private val edit = view.findViewById<AppCompatImageView>(R.id.edit)
        private val save = view.findViewById<AppCompatImageView>(R.id.save)
        private val remove = view.findViewById<AppCompatImageView>(R.id.remove)

        fun populate(position: Int) = with(list[position]){

            edit.setOnClickListener {
                it.visibility = View.GONE ; save.visibility = View.VISIBLE
                weightField.isEnabled = true ; polishField.isEnabled = true
                labourField.isEnabled = true
            }

            save.setOnClickListener {

            }

            remove.setOnClickListener { list.removeAt(position); notifyDataSetChanged() }

            itemName.text = name ; weightField.setText(weight)
            polishField.setText(polishCharge.toString()) ; labourField.setText(labour.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_bill_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populate(position)
    }

    override fun getItemCount() = 0

}