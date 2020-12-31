package com.dawar.jewellerybilling.print

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils
import com.dawar.jewellerybilling.databinding.ActivityPrinterSettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PrinterSettingActivity : AppCompatActivity() {

    private val viewModel by viewModels<BluetoothPrintViewModel>()
    private lateinit var binding :ActivityPrinterSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer_setting)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_printer_setting)
        binding.lifecycleOwner = this

        binding.saveName.setOnClickListener {
            val name =  binding.printerName.text.toString()
            if(name.isNotEmpty()) Utils.updatePrinterName(this,name).also {
                finish()
                Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setStatus(connected:Boolean){
        binding.connecting.visibility = View.GONE
        if(connected){
            binding.connStatus.text = "Connected"
            binding.connStatus.setTextColor(Color.GREEN)
            binding.testPrint.visibility = View.VISIBLE
        }
        else {
            binding.connStatus.text = "Not Connected"
            binding.connStatus.setTextColor(Color.RED)
            binding.testPrint.visibility = View.GONE
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK) viewModel.initPrint()
    }

    fun goBack(view: View) = finish()
}