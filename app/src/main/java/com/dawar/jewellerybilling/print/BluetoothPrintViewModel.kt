package com.dawar.jewellerybilling.print

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.Utils
import kotlinx.coroutines.launch

class BluetoothPrintViewModel : ViewModel() {

    val isConnected = MutableLiveData<Boolean>().apply { value = JewelloBluetoothSocket.isConnected() }
    val isConnecting = MutableLiveData<Boolean>().apply { value = false }

    fun connect(context: Context,printerName:String) = viewModelScope.launch{
        if(printerName.isNotBlank()) Utils.printerName = printerName
        isConnecting.value = true
        JewelloBluetoothSocket.findDeviceAndConnect(context)
        isConnecting.value = false
        isConnected.value = JewelloBluetoothSocket.isConnected()
    }

    fun disconnect() {
        JewelloBluetoothSocket.disconnectBT()
        isConnected.value = false
    }

    fun connectToPrinter(context: Context) = viewModelScope.launch{
        JewelloBluetoothSocket.findDeviceAndConnect(context)
    }
}
