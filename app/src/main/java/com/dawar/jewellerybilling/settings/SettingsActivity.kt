package com.dawar.jewellerybilling.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.startActivity
import com.dawar.jewellerybilling.bills.BillsActivity
import com.dawar.jewellerybilling.customers.listActivity.CustomersActivity
import com.dawar.jewellerybilling.databinding.ActivitySettingsBinding
import com.dawar.jewellerybilling.items.listItems.ItemsActivity
import com.dawar.jewellerybilling.print.PrinterSettingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    @Inject lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.lifecycleOwner = this
        val list = listOf("Items", "Bills", "Customers", "Printer"/*, "Sales"*/)
        binding.listView.adapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list)
        binding.listView.onItemClickListener = this
        binding.bName = sharedPreferences.getString("business_name","")
    }

    fun goBack(v: View) = finish()

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> startActivity(ItemsActivity::class.java)
            1 -> startActivity(BillsActivity::class.java)
            2 -> startActivity(CustomersActivity::class.java)
            3 -> startActivity(PrinterSettingActivity::class.java)
        }
    }

    fun changeBusinessName(v: View){
        val name = binding.businessName.text.toString()

        if(name.isNotBlank()){
            sharedPreferences.edit().putString("business_name",name).apply()
            binding.updateBusinessName = false
            binding.bName = name
        }
    }

    fun cancelChangingName(v: View){
        binding.updateBusinessName = false
    }

    fun changeName(v:View){
        binding.updateBusinessName = true
    }

}