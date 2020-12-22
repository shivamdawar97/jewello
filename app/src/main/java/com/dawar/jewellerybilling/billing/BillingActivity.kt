package com.dawar.jewellerybilling.billing

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.animationListener
import com.dawar.jewellerybilling.databinding.ActivityBillingBinding
import com.dawar.jewellerybilling.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBillingBinding
    private val viewModel: BillingViewModel by viewModels()

    private val showAnimation by lazy {
        val anim = AnimationUtils.loadAnimation(this,R.anim.in_from_bottom)
        anim.setAnimationListener(animationListener { binding.itemSelector.visibility = View.VISIBLE })
        anim
    }

    private val hideAnimation by lazy {
        val anim= AnimationUtils.loadAnimation(this,R.anim.out_to_bottom)
        anim.setAnimationListener(animationListener { binding.itemSelector.visibility = View.INVISIBLE })
        anim
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_billing)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setUpAutoCompleteCustomerNameEditText()
        setUpItemSelector()
        setOptionsMenu()
    }

    private fun setUpAutoCompleteCustomerNameEditText() {
        viewModel.customers.observeForever { customers ->
            val customerNamesAdapter = ArrayAdapter(
                this,
                android.R.layout.select_dialog_item,
                customers.map { it.name })
            binding.customerName.setAdapter(customerNamesAdapter)
        }
        binding.customerName.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    private fun setUpItemSelector() {
        viewModel.items.observeForever {
            binding.itemSelector.updateItems(it)
        }
        binding.itemSelector.setOnItemSelectedListener {

        }
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

    fun gotoSettings(v: View) = startActivity(Intent(this, SettingsActivity::class.java))

    fun openItemSelector(v:View) = binding.itemSelector.startAnimation(showAnimation)

    fun closeSelector(v:View) = binding.itemSelector.startAnimation(hideAnimation)

    fun reset() = binding.customerName.setText("")


}