package com.dawar.jewellerybilling.print

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dawar.jewellerybilling.R
import com.dawar.jewellerybilling.Utils
import com.dawar.jewellerybilling.databinding.ActivityPrinterSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrinterSettingActivity : AppCompatActivity() {

    private val viewModel by viewModels<BluetoothPrintViewModel>()
    private lateinit var binding :ActivityPrinterSettingBinding
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer_setting)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_printer_setting)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.printerName = Utils.printerName
        viewModel.connectToPrinter(this)
    }

    fun saveSetting(v:View) = with(binding.printerName){
        if(!this.isNullOrBlank())  {
            Utils.updatePrinterName(this,sharedPreferences)
            finish()
            Toast.makeText(this@PrinterSettingActivity,"Saved",Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK) viewModel.connect(this,binding.printerName!!)
    }

    fun goBack(view: View) = finish()

    fun connect(view: View) { viewModel.connect(this,binding.printerName!!) }
}