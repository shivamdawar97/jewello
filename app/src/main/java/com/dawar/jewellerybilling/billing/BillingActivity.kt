package com.dawar.jewellerybilling.billing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import com.dawar.jewellerybilling.R
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.databinding.ActivityBillingBinding
import com.dawar.jewellerybilling.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBillingBinding
    private val viewModel:BillingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_billing)
        binding.lifecycleOwner = this
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

    fun gotoSettings(v: View){
        startActivity(Intent(this,SettingsActivity::class.java))
    }


}