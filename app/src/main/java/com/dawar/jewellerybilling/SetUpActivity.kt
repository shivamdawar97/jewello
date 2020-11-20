package com.dawar.jewellerybilling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dawar.jewellerybilling.billing.BillingActivity

class SetUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up)
    }

    fun getStarted(v: View) {
        startActivity(Intent(this, BillingActivity::class.java))
    }
}