package com.dawar.jewellerybilling.print

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.Utils
import kotlinx.coroutines.launch

class BluetoothPrintViewModel : ViewModel() {

    val bluetoothSocket = JewelloBluetoothSocket()
    val isConnected = MutableLiveData<Boolean>().apply { value = bluetoothSocket.isConnected() }
    val isConnecting = MutableLiveData<Boolean>().apply { value = false }

    fun connect(printerName:String="") = viewModelScope.launch{
        if(printerName.isNotBlank()) Utils.printerName = printerName
        isConnecting.value = true
        isConnecting.value = false
        isConnected.value = bluetoothSocket.isConnected()
    }

    fun disconnect() {
        bluetoothSocket.disconnectBT()
        isConnected.value = false
    }

    fun connectToPrinter(context: Context) = viewModelScope.launch{
        bluetoothSocket.findDeviceAndConnect(context)
    }
}
