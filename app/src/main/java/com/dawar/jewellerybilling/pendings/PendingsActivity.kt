package com.dawar.jewellerybilling.pendings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils
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
                view.findViewById<TextView>(R.id.pending_customer_name).text = pending.customerName
                view.findViewById<TextView>(R.id.pending_total_amount).text = pending.totalAmount.toString()
                view.findViewById<TextView>(R.id.pending_amount_received).text = pending.amountReceived.toString()
                findViewById<Button>(R.id.restore).setOnClickListener {

                }
                findViewById<Button>(R.id.delete).setOnClickListener {

                }
            }
        }
    }

    fun goBack(v: View) = finish()

    fun deleteALl(v:View) = viewModel.deleteAll()
}