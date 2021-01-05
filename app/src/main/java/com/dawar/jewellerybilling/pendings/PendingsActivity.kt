package com.dawar.jewellerybilling.pendings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dawar.jewellerybilling.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendings)

    }

    fun goBack(v: View) = finish()
}