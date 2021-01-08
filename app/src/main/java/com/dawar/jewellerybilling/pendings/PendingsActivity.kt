package com.dawar.jewellerybilling.pendings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils
import com.dawar.jewellerybilling.rx.RxBus
import com.dawar.jewellerybilling.rx.RxEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingsActivity : AppCompatActivity() {

    private val viewModel by viewModels<PendingViewModel>()
    private val pendingRecycler by lazy { findViewById<RecyclerView>(R.id.pending_recycler) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendings)
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        pendingRecycler.layoutManager = LinearLayoutManager(this)
        viewModel.pending.observeForever {
            pendingRecycler.adapter = Utils.generatedAdapter(it,R.layout.card_pending){
                pending, view ->
                val ll = view.findViewById<LinearLayout>(R.id.pending_items_container)
                ll.removeAllViews()
                pending.items.forEach { item ->
                    val card = LayoutInflater.from(this).inflate(R.layout.card_bill_item,null)
                    card.findViewById<EditText>(R.id.item_weight).setText(item.weight.toString())
                    card.findViewById<EditText>(R.id.polish_charge).setText(item.item.polishCharge.toString())
                    card.findViewById<EditText>(R.id.labour).setText(item.item.labour).toString()
                    card.findViewById<AppCompatImageView>(R.id.edit).visibility = View.GONE
                    card.findViewById<AppCompatImageView>(R.id.remove).visibility = View.GONE
                    ll.addView(card)
                }
                view.findViewById<TextView>(R.id.pending_customer_name).text = pending.customerName
                view.findViewById<TextView>(R.id.pending_amount_received).text = pending.amountReceived.toString()
                findViewById<Button>(R.id.restore).setOnClickListener {
                    RxBus.publish(RxEvent.PendingRestored(pending))
                }
                findViewById<Button>(R.id.delete).setOnClickListener {
                    viewModel.delete(pending)
                }
            }
        }
    }

    fun goBack(v: View) = finish()

    fun deleteALl(v:View) = viewModel.deleteAll()
}