package com.dawar.jewellerybilling.customviews.itemSelector

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.onTextChanged
import com.dawar.jewellerybilling.database.entities.Item

class ItemSelector : LinearLayout{

    private lateinit var itemsRecycler: RecyclerView
    private lateinit var itemSelectorAdapter: ItemSelectorAdapter

    constructor(context: Context) : super(context) {
        inflateLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflateLayout(context)
    }

    fun updateItems(newItems: List<Item>) = itemSelectorAdapter.updateItems(newItems)

    fun setOnItemSelectedListener(listener: (Item) -> Unit) {
        itemSelectorAdapter = ItemSelectorAdapter(ArrayList<Item>(),listener)
        itemsRecycler.adapter = itemSelectorAdapter
    }

    private fun inflateLayout(context: Context) {
        inflate(context, R.layout.view_item_selector, this)

        itemsRecycler = findViewById(R.id.items_recycler_view)
        itemsRecycler.layoutManager = GridLayoutManager(context, 4)

        val search = findViewById<EditText>(R.id.search_field)
        search.onTextChanged { itemSelectorAdapter.filter.filter(it) }
    }

}
