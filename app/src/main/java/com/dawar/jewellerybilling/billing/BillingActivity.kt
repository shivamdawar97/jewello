package com.dawar.jewellerybilling.billing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import com.dawar.jewellerybilling.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dawar.jewellerybilling.database.JewelloDatabase
import com.dawar.jewellerybilling.databinding.ActivityBillingBinding

class BillingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBillingBinding
    private lateinit var viewModel: BillingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_billing)
        binding.lifecycleOwner = this
        val factory = BillingViewModelFactory(this)
        viewModel = ViewModelProvider(this,factory).get(BillingViewModel::class.java)
        binding.viewModel = viewModel

        setOptionsMenu()
    }

    private fun setOptionsMenu() = PopupMenu(applicationContext, binding.optionsMenu).apply {
        inflate(R.menu.bill_options_menu)
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_send_to_pending -> {

                }
                R.id.option_view_pending -> {

                }
                else -> {
                }
            }
            return@setOnMenuItemClickListener true
        }
        binding.optionsMenu.setOnClickListener { show() }

    }


}