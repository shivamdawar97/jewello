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
import com.dawar.jewellerybilling.Utils.startActivity
import com.dawar.jewellerybilling.databinding.ActivityBillingBinding
import com.dawar.jewellerybilling.pendings.PendingsActivity
import com.dawar.jewellerybilling.print.JewelloBluetoothSocket
import com.dawar.jewellerybilling.print.printBill.PrintBillActivity
import com.dawar.jewellerybilling.rx.RxBus
import com.dawar.jewellerybilling.rx.RxEvent
import com.dawar.jewellerybilling.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class BillingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBillingBinding
    private val viewModel: BillingViewModel by viewModels()

    private lateinit var showAnimation: Animation
    private lateinit var hideAnimation: Animation
    private lateinit var rxPending: Disposable

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
        viewModel.connectToPrinter(this)

        rxPending = RxBus.listen(RxEvent.PendingRestored::class.java).subscribe {
            viewModel.customerName.value = it.pending.customerName
            (binding.billItemsRecyclerView.adapter as BillItemsRecyclerViewAdapter).addItems(it.pending.items)
        }
    }


    private fun setupBillItemsRecyclerView() {
        binding.billItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.billItemsRecyclerView.adapter =
            BillItemsRecyclerViewAdapter(ArrayList<BillItem>()) { goldWeight, silverWeight, labour ->
                viewModel.updateWeights(goldWeight, silverWeight, labour)
            }
    }

    private fun setUpAutoCompleteCustomerNameEditText() {
        viewModel.customers.observeForever { customers ->
            val customerNamesAdapter =
                ArrayAdapter(this, android.R.layout.select_dialog_item, customers.map { "${it.customerId} ${it.name}" })
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
            closeSelector(null); hideKeyboard(binding.itemSelector)
        }
        viewModel.items.observeForever {
            binding.itemSelector.updateItems(it)
        }
    }

    private fun setOptionsMenu() = PopupMenu(applicationContext, binding.optionsMenu).apply {
        inflate(R.menu.bill_options_menu)
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_view_pending -> startActivity(PendingsActivity::class.java)
                else -> if(viewModel.lastBillNo.value!! != 0) { //option_goto_last_bill
                    val intent = Intent(this@BillingActivity, PrintBillActivity::class.java)
                    intent.putExtra("billId", viewModel.lastBillNo.value!!.toLong())
                    startActivity(intent)
                }
            }
            return@setOnMenuItemClickListener true
        }
        binding.optionsMenu.setOnClickListener { show() }
    }

    private fun initializeAnimations() {
        showAnimation = AnimationUtils.loadAnimation(this, R.anim.in_from_bottom)
        showAnimation.setAnimationListener(animationListener {
            binding.itemSelector.visibility = View.VISIBLE
        })
        hideAnimation = AnimationUtils.loadAnimation(this, R.anim.out_to_bottom)
        hideAnimation.setAnimationListener(animationListener {
            binding.itemSelector.visibility = View.INVISIBLE
        })
    }

    private fun setUpObservers() {
        viewModel.goldRate.observeForever { binding.goldRate = it.toString() }
        viewModel.silverRate.observeForever { binding.silverRate = it.toString() }
    }

    fun saveBill(v: View) = viewModel.saveBill(getBillItemsList()) { billId ->
        startActivity(Intent(this, PrintBillActivity::class.java).putExtra("billId", billId))
        reset(null)
    }


    private fun getBillItemsList() =
        (binding.billItemsRecyclerView.adapter as BillItemsRecyclerViewAdapter).getItemList()

    fun gotoSettings(v: View) = startActivity(Intent(this, SettingsActivity::class.java))

    fun openItemSelector(v: View) = binding.itemSelector.startAnimation(showAnimation)

    fun closeSelector(v: View?) = binding.itemSelector.startAnimation(hideAnimation)

    fun reset(v: View?) {
        (binding.billItemsRecyclerView.adapter as BillItemsRecyclerViewAdapter).clear()
        viewModel.reset()
    }

    fun sendToPending(v:View){
        viewModel.sendBillToPending(getBillItemsList())
        reset(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        rxPending.dispose()
    }

}