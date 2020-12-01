package com.dawar.jewellerybilling.customers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dawar.jewellerybilling.R

class CustomersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers)
    }

    fun goBack(v: View) = finish()

    fun addCustomer(v:View) =  startActivity(Intent(this,AddCustomerActivity::class.java))
}