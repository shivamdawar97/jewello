package com.dawar.jewellerybilling.items.listItems

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.onTextChanged
import com.dawar.jewellerybilling.customers.listActivity.CustomersRecyclerViewAdapter
import com.dawar.jewellerybilling.customers.listActivity.CustomersViewModel
import com.dawar.jewellerybilling.databinding.ActivityCustomersBinding
import com.dawar.jewellerybilling.databinding.ActivityItemsBinding
import com.dawar.jewellerybilling.items.addItem.AddItemActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsActivity : AppCompatActivity() {

    private val viewModel: ItemsViewModel by viewModels()
    private lateinit var binding : ActivityItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_items)
        binding.itemsRecycler.layoutManager = LinearLayoutManager(this)
        viewModel.items.observeForever {
            binding.itemsRecycler.adapter = ItemsRecyclerViewAdapter(it)
        }
        binding.searchFiled.onTextChanged {
            (binding.itemsRecycler.adapter as ItemsRecyclerViewAdapter).filter.filter(it)
        }
    }

    fun goBack(v: View) = finish()

    fun addItem(v: View) = startActivity(Intent(this,AddItemActivity::class.java))
}