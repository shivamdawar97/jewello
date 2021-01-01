package com.dawar.jewellerybilling.bills

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.dawar.jewellerybilling.database.daos.BillDao

class BillsActivityViewModel @ViewModelInject constructor(
    billDao: BillDao):ViewModel() {

    val billList = billDao.billByDates().toLiveData(pageSize = 50)

}