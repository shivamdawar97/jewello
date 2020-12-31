package com.dawar.jewellerybilling.print

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dawar.jewellerybilling.R

class PrintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)
    }

    fun goBack(v: View) = finish()
}