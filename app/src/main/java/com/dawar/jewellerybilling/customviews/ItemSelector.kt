package com.dawar.jewellerybilling.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.customers.listActivity.CustomersRecyclerViewAdapter
import com.dawar.jewellerybilling.database.entities.Item

class ItemSelector : LinearLayout {

    private lateinit var itemsRecycler: RecyclerView
    private lateinit var search: EditText
    lateinit var itemSelectorRecyclerView: ItemSelectorRecyclerView

    constructor(context: Context) : super(context) {
        inflateLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        inflateLayout(context)
    }

//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr,defStyleRes) {inflateLayout(context)}

    private fun inflateLayout(context: Context) {
        inflate(context, R.layout.view_item_selector, this)
        itemsRecycler = findViewById(R.id.items_recycler_view)
        itemsRecycler.layoutManager = GridLayoutManager(context, 4)

    }

}

class ItemSelectorRecyclerView(private val items: List<Item>) :
    RecyclerView.Adapter<ItemSelectorRecyclerView.ViewHolder>(), Filterable {
    private var filteredItems = items

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item_selector, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = filteredItems.size

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}
