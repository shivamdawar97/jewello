package com.dawar.jewellerybilling.bills

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dawar.jewellerybilling.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bills)
    }
}