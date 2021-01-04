package com.dawar.jewellerybilling.print.printBill

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.Utils
import com.dawar.jewellerybilling.Utils.getFormattedDate
import com.dawar.jewellerybilling.billing.BillingRepository
import com.dawar.jewellerybilling.database.entities.Bill
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.print.JewelloBluetoothSocket
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.*

class PrintBillViewModel @ViewModelInject constructor(
    private val billingRepository: BillingRepository,
    private val bluetoothSocket: JewelloBluetoothSocket
    ): ViewModel() {

    val billId = MutableLiveData<Long>()
    val bill = MediatorLiveData<Bill>().apply {
        addSource(billId){
            viewModelScope.launch{
                value = billingRepository.getBillById(billId.value!!)
            }
        }
    }

    fun printBill() {
        bluetoothSocket.testPrint()
        with(bill.value!!){
            val stringBuilder = StringBuilder()
            stringBuilder.append("\t\t\t ${Utils.bussinessName}")
            stringBuilder.append("Bill Estimation\n")
            stringBuilder.append("Bill No $billId\n")
            stringBuilder.append("$customerName \n")
            stringBuilder.append(Date(date).getFormattedDate())

            stringBuilder.append("\n ------------------------\n")
            items.forEach {
                stringBuilder.append("${it.item.name}\n")
                stringBuilder.append("Weight: ${it.weight}")
                if(it.item.polishCharge!=0f) stringBuilder.append("Polish & making charge: ${it.item.polishCharge}\n")
                if(it.item.labour!=0) stringBuilder.append("Labour: ${it.item.labour}")
            }
            stringBuilder.append("\n ------------------------\n")

            stringBuilder.append("Total Amount: $totalAmount")
            stringBuilder.append("Amount Received: $amountReceived")
            stringBuilder.append("Balance: $balanceAmount")
            stringBuilder.append("\n\n\n")
            bluetoothSocket.printData(stringBuilder.toString())
        }
    }

}