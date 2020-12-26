package com.dawar.jewellerybilling.billing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils.animationListener
import com.dawar.jewellerybilling.Utils.hideKeyboard
import com.dawar.jewellerybilling.databinding.ActivityBillingBinding
import com.dawar.jewellerybilling.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBillingBinding
    private val viewModel: BillingViewModel by viewModels()

    private lateinit var showAnimation : Animation
    private lateinit var hideAnimation : Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_billing)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupBillItemsRecyclerView()
        setUpAutoCompleteCustomerNameEditText()
        setUpItemSelector()
        setOptionsMenu()
        initializeAnimations()
        setUpObservers()
    }


    private fun setupBillItemsRecyclerView() {
        binding.billItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.billItemsRecyclerView.adapter = BillItemsRecyclerViewAdapter(ArrayList<BillItem>()){
            goldWeight,silverWeight,labour -> viewModel.updateWeights(goldWeight,silverWeight,labour)
        }
    }

    private fun setUpAutoCompleteCustomerNameEditText() {
        viewModel.customers.observeForever { customers ->
            val customerNamesAdapter =
                ArrayAdapter(this, android.R.layout.select_dialog_item, customers.map { it.name })
            binding.customerName.setAdapter(customerNamesAdapter)
        }

        binding.customerName.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            this.currentFocus?.let { hideKeyboard(it) }
            getSystemService(Context.INPUT_METHOD_SERVICE)
        }
    }

    private fun setUpItemSelector() {
        binding.itemSelector.setOnItemSelectedListener {
            (binding.billItemsRecyclerView.adapter as BillItemsRecyclerViewAdapter).addItem(it)
            closeSelector(null) ; hideKeyboard(binding.itemSelector)
        }
        viewModel.items.observeForever {
            binding.itemSelector.updateItems(it)
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

    private fun initializeAnimations() {
        showAnimation = AnimationUtils.loadAnimation(this,R.anim.in_from_bottom)
        showAnimation.setAnimationListener(animationListener { binding.itemSelector.visibility = View.VISIBLE })
        hideAnimation= AnimationUtils.loadAnimation(this,R.anim.out_to_bottom)
        hideAnimation.setAnimationListener(animationListener { binding.itemSelector.visibility = View.INVISIBLE })
    }

    private fun setUpObservers() {
        viewModel.goldRate.observeForever { binding.goldRate = it.toString() }
        viewModel.silverRate.observeForever { binding.silverRate = it.toString() }
    }

    fun gotoSettings(v: View) = startActivity(Intent(this, SettingsActivity::class.java))

    fun openItemSelector(v:View) = binding.itemSelector.startAnimation(showAnimation)

    fun closeSelector(v:View?) = binding.itemSelector.startAnimation(hideAnimation)

    fun reset(v:View) {
        (binding.billItemsRecyclerView.adapter as BillItemsRecyclerViewAdapter).clear()
        viewModel.reset()
    }



}