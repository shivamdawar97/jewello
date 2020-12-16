package com.dawar.jewellerybilling.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.customers.listActivity.CustomersActivity
import com.dawar.jewellerybilling.databinding.ActivitySettingsBinding
import com.dawar.jewellerybilling.items.listItems.ItemsActivity

class SettingsActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.lifecycleOwner = this
        val list = listOf("Items","Bills","Customers","Printer")
        binding.listView.adapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list)
        binding.listView.onItemClickListener = this
    }

    fun goBack(v: View) = finish()

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 -> startActivity(Intent(this, ItemsActivity::class.java))
            1 -> {}
            2 -> startActivity(Intent(this, CustomersActivity::class.java))
            3 -> {}
        }
    }


}