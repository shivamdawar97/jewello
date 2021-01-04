package com.dawar.jewellerybilling

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dawar.jewellerybilling.billing.BillingActivity
import com.dawar.jewellerybilling.print.JewelloBluetoothSocket
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        waitAndLaunch()

    }

    private fun waitAndLaunch() = CoroutineScope(Dispatchers.Main).launch {
        delay(500)
        Utils.printerName = sharedPreferences.getString("printer_name","")?:""
        Utils.bussinessName = sharedPreferences.getString("business_name","")?:""
        val isRegistered = Utils.bussinessName.isNotBlank()

        if(isRegistered)  startActivity(Intent(this@MainActivity,BillingActivity::class.java))
        else startActivity(Intent(this@MainActivity,SetUpActivity::class.java))
        finish()
    }
}