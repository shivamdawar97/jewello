package com.dawar.jewellerybilling.print

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.UUID

class BluetoothPrintViewModel(private val context: Context) : ViewModel() {

    private var bluetoothDevice: BluetoothDevice? = null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null
    private var readBuffer: ByteArray? = null
    private var readBufferPosition: Int = 0

    @Volatile
    private var stopWorker: Boolean = false

    companion object {
        private var socket: BluetoothSocket? = null
    }

    val isConnected = MutableLiveData<Boolean>().apply { value = false }
    val isConnecting = MutableLiveData<Boolean>().apply { value = false }

    init {
        socket?.let {
            isConnected.value = it.isConnected
        }
        initPrint()
    }

    fun initPrint() = viewModelScope.launch {
        isConnecting.value = true
        if (Utils.printerName == "") return@launch

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        try {
            if (!bluetoothAdapter.isEnabled) {
                val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                (context as Activity).startActivityForResult(enableBluetooth, 0)
                isConnecting.value = false
                return@launch
            }

            val pairedDevices = bluetoothAdapter.bondedDevices

            if (pairedDevices.size > 0) startConnection(pairedDevices, bluetoothAdapter)
            else Toast.makeText(context, "No Devices found", Toast.LENGTH_LONG).show()


        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        isConnecting.value = false
    }

    private suspend fun startConnection(
        pairedDevices: MutableSet<BluetoothDevice>,
        bluetoothAdapter: BluetoothAdapter
    ) {
        for (device in pairedDevices) if (device.name == Utils.printerName) {
            bluetoothDevice = device
            break
        }

        val uuid =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") //Standard SerialPortService ID
        if (socket == null) socket = withContext(Dispatchers.Default) {
            bluetoothDevice!!.createRfcommSocketToServiceRecord(uuid)
        }
        bluetoothAdapter.cancelDiscovery()
        withContext(Dispatchers.Default) {
            socket?.connect()
            isConnected.value = true
        }
        outputStream = socket!!.outputStream
        inputStream = socket!!.inputStream
        beginListenForData()
    }

    private fun beginListenForData() {

        try {

            val handler = Handler()

            // this is the ASCII code for a newline character
            val delimiter: Byte = 10

            stopWorker = false
            readBufferPosition = 0
            readBuffer = ByteArray(1024)

            // specify US-ASCII encoding
            // tell the user data were sent to bluetooth printer device
            val workerThread = Thread {
                while (!Thread.currentThread().isInterrupted && !stopWorker) {

                    try {

                        val bytesAvailable = inputStream!!.available()

                        if (bytesAvailable > 0) {

                            val packetBytes = ByteArray(bytesAvailable)
                            inputStream!!.read(packetBytes)

                            for (i in 0 until bytesAvailable) {

                                val b = packetBytes[i]
                                if (b == delimiter) {

                                    val encodedBytes = ByteArray(readBufferPosition)
                                    System.arraycopy(
                                        readBuffer!!, 0,
                                        encodedBytes, 0,
                                        encodedBytes.size
                                    )

                                    // specify US-ASCII encoding
                                    val data = String(encodedBytes, Charset.forName("US-ASCII"))
                                    readBufferPosition = 0

                                    // tell the user data were sent to bluetooth printer device
                                    handler.post { Log.d("Bluetooth_Error", data) }

                                } else {
                                    readBuffer!![readBufferPosition++] = b
                                }
                            }
                        }

                    } catch (ex: IOException) {
                        stopWorker = true
                    }

                }
            }

            workerThread.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun printData(txt: String) = try {
        val text = "${Utils.bussinessName}\n $txt  \n\n\n"
        val buffer = text.toByteArray()
        outputStream!!.write(buffer, 0, buffer.size)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    @Throws(IOException::class)
    internal fun disconnectBT() {
        try {
            stopWorker = true
            outputStream!!.close()
            inputStream!!.close()
            socket!!.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

}
