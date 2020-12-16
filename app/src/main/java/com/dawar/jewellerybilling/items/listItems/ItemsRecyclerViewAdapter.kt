package com.dawar.jewellerybilling.items.listItems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.database.entities.Item

class ItemsRecyclerViewAdapter (private val items: List<Item>) :
    RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder>(), Filterable
{

    private var filteredItems = items

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val idView = view.findViewById<TextView>(R.id.item_id)!!
        private val nameView = view.findViewById<TextView>(R.id.item_name)!!
        private val polishView = view.findViewById<TextView>(R.id.polish_charge)!!
        private val labourView = view.findViewById<TextView>(R.id.labour)!!

        fun populateCard(item: Item) = with(item){
            idView.text = itemId.toString()
            nameView.text = name.toString()
            polishView.text = polishCharge.toString()
            labourView.text = labour.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateCard(filteredItems[position])
    }


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
