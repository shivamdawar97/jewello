package com.dawar.jewellerybilling.customviews.itemSelector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.database.entities.Item

class ItemSelectorAdapter(var items: List<Item>,private val onItemSelectedListener: (Item) -> Unit) :
    RecyclerView.Adapter<ItemSelectorAdapter.ViewHolder>(), Filterable
{
    private var filteredItems = items

    fun updateItems(newItems: List<Item>){
        items = newItems
        filteredItems = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun populate(item:Item) {
            (view as TextView).text = item.name
            view.setOnClickListener { onItemSelectedListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item_selector,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.populate(filteredItems[position])

    override fun getItemCount() = filteredItems.size

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val cs = constraint.toString()
            filteredItems = if(cs.isEmpty()) items else {
                val filteredList = ArrayList<Item>()
                items.forEach {
                    if(it.name.toLowerCase().contains(cs.toLowerCase()))
                        filteredList.add(it)
                }
                filteredList
            }
            return FilterResults().also { it.values = filteredItems }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if(results?.values != null){
                filteredItems = results.values as List<Item>
                notifyDataSetChanged()
            }
        }

    }

}