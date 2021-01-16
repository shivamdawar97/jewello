package com.dawar.jewellerybilling.billing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.getTextToFloat
import com.dawar.jewellerybilling.Utils.getTextToInt
import com.dawar.jewellerybilling.Utils.toMannerString
import com.dawar.jewellerybilling.database.entities.Item

class BillItemsRecyclerViewAdapter(
    private val list: ArrayList<BillItem>,
    private val onUpdatedListener: (Float, Float, Int) -> Unit
) : RecyclerView.Adapter<BillItemsRecyclerViewAdapter.ViewHolder>() {

    fun addItem(newItem: Item) {
        list.add(BillItem(newItem, 0f))
        notifyDataSetChanged()
        calculate()
    }

    fun addItems(newItems:List<BillItem>){
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
        calculate()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val itemName = view.findViewById<TextView>(R.id.item_name)
        private val weightField = view.findViewById<EditText>(R.id.item_weight)
        private val polishField = view.findViewById<EditText>(R.id.polish_charge)
        private val labourField = view.findViewById<EditText>(R.id.labour)
        private val edit = view.findViewById<AppCompatImageView>(R.id.edit)
        private val save = view.findViewById<AppCompatImageView>(R.id.save)
        private val remove = view.findViewById<AppCompatImageView>(R.id.remove)

        fun populate(position: Int) = with(list[position]) {
            itemName.text = item.name; weightField.setText(weight.toMannerString())
            polishField.setText(item.polishCharge.toMannerString()); labourField.setText(item.labour.toMannerString())

            edit.setOnClickListener {
                it.visibility = View.GONE; save.visibility = View.VISIBLE
                weightField.isEnabled = true; polishField.isEnabled = true
                labourField.isEnabled = true
            }

            save.setOnClickListener {

                weight = weightField.getTextToFloat()
                item.polishCharge = polishField.getTextToFloat()
                item.labour = labourField.getTextToInt()

                it.visibility = View.GONE; edit.visibility = View.VISIBLE
                weightField.isEnabled = false; polishField.isEnabled = false
                labourField.isEnabled = false

                this@BillItemsRecyclerViewAdapter.calculate()
            }

            remove.setOnClickListener {
                list.removeAt(position)
                notifyDataSetChanged()
                this@BillItemsRecyclerViewAdapter.calculate()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_bill_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populate(position)
    }

    override fun getItemCount() = list.size

    private fun calculate() {
        var gold = 0f
        var silver = 0f
        var labour = 0
        list.forEach {
            val weight = it.weight + it.item.polishCharge
            if (it.item.isGold) gold += weight  else silver += weight
            labour += it.item.labour
        }
        onUpdatedListener(gold,silver,labour)
    }

    fun clear() = list.clear()

    fun getItemList() = list


}