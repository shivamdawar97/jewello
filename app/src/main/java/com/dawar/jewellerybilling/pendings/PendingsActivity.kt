package com.dawar.jewellerybilling.pendings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.dawar.jewellerybilling.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingsActivity : AppCompatActivity() {

    private val viewModel by viewModels<PendingViewModel>()
    private val pendingRecycler by lazy { findViewById<RecyclerView>(R.id.pending_recycler) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendings)

    }

    fun goBack(v: View) = finish()

    fun deleteALl(v:View) = viewModel.deleteAll()
}