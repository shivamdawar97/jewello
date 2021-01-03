package com.dawar.jewellerybilling

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.dawar.jewellerybilling.billing.BillingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetUpActivity : AppCompatActivity() {

    @Inject lateinit var sharedPreferences: SharedPreferences
    private val businessName by lazy { findViewById<EditText>(R.id.business_name) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up)

    }

    fun getStarted(v: View) = businessName.text.toString().let {
        if(it.isNotBlank()) {
            sharedPreferences.edit().putString("business_name",it).apply()
            startActivity(Intent(this, BillingActivity::class.java))
        }
    }
}