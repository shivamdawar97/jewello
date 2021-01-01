package com.dawar.jewellerybilling.print.printBill

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.billing.BillingRepository
import com.dawar.jewellerybilling.database.entities.Bill
import com.dawar.jewellerybilling.database.entities.Customer
import kotlinx.coroutines.launch

class PrintBillViewModel @ViewModelInject constructor(
    private val billingRepository: BillingRepository): ViewModel() {

    fun getBillAndCustomer(billId:Long,callback:(Bill,Customer)-> Unit) = viewModelScope.launch{
        val bill = billingRepository.getBillById(billId)
        val customer = billingRepository.getCustomerById(bill.customerId)
        callback(bill,customer)
    }

}