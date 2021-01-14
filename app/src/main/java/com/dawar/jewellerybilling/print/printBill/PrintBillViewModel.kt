package com.dawar.jewellerybilling.print.printBill

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.Utils
import com.dawar.jewellerybilling.Utils.getFormattedDate
import com.dawar.jewellerybilling.billing.BillingRepository
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.entities.Bill
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.print.JewelloBluetoothSocket
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.*

class PrintBillViewModel @ViewModelInject constructor(
    private val billingRepository: BillingRepository,
    private val customerRepository: CustomerRepository
    ): ViewModel() {
    private var customer:Customer? = null
    val billId = MutableLiveData<Long>()
    val bill = MediatorLiveData<Bill>().apply {
        addSource(billId){
            viewModelScope.launch{
                val bill = billingRepository.getBillById(billId.value!!)
                customer = customerRepository.getCustomer(bill.customerId)
                value = bill
            }
        }
    }

    fun printBill() = with(bill.value!!){
        val stringBuilder = StringBuilder()
        .append("Bill Estimation\n")
        .append("Bill No $billId\n")
        .append("$customerName \n")
        .append(Date(date).getFormattedDate())
        .append("\n----------------------------\n")
        items.forEach {
            stringBuilder.append("${it.item.name}\n")
            .append("Weight: ${it.weight}")
            if(it.item.polishCharge!=0f) stringBuilder.append("Polish & making charge: ${it.item.polishCharge}\n")
            if(it.item.labour!=0) stringBuilder.append("Labour: ${it.item.labour}")
        }
        stringBuilder.append("\n----------------------------\n")
        .append("Gold Rate (Bhav): $goldRate")
        .append("Silver Rate (Bhav): $silverRate")
        .append("Total Amount: $totalAmount\n")
        .append("Amount Received: $amountReceived\n")
        .append("Balance: $balanceAmount\n")
        if(customer!=null) stringBuilder.append("Total Due balance: ${customer!!.balance}")
        .append("\n\n")
        JewelloBluetoothSocket.printData(stringBuilder.toString())
    }
}