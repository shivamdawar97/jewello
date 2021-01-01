package com.dawar.jewellerybilling.print.printBill

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.billing.BillingRepository
import com.dawar.jewellerybilling.database.entities.Bill
import com.dawar.jewellerybilling.database.entities.Customer
import kotlinx.coroutines.launch
import java.util.*

class PrintBillViewModel @ViewModelInject constructor(
    private val billingRepository: BillingRepository): ViewModel() {

    val billId = MutableLiveData<Long>()
    val bill = MediatorLiveData<Bill>().apply {
        addSource(billId){
            viewModelScope.launch{
                value = billingRepository.getBillById(billId.value!!)
            }
        }
    }

    fun getDateStringFromLong(date:Long) = Date(date).toString().slice(IntRange(0,19))
}