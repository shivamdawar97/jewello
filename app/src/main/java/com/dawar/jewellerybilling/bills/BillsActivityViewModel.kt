package com.dawar.jewellerybilling.bills


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.Utils.getFormattedDate
import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.entities.Bill
import kotlinx.coroutines.launch
import java.util.*

class BillsActivityViewModel @ViewModelInject constructor(
    private val billDao: BillDao
) : ViewModel() {

    val date = MutableLiveData<Date>().apply { value = Date() }
    val bills = MediatorLiveData<List<Bill>>().apply {
        addSource(date) {
            viewModelScope.launch {
                value = billDao.billsByDate(atStartOfDay(it).time, atEndOfDay(it).time)
            }
        }
    }

    private fun atEndOfDay(date: Date) = with(Calendar.getInstance()) {
        time = date
        set(Calendar.HOUR_OF_DAY, 23);set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59);set(Calendar.MILLISECOND, 999)
        time
    }

    private fun atStartOfDay(date: Date) = with(Calendar.getInstance()) {
        time = date
        set(Calendar.HOUR_OF_DAY, 0);set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0);set(Calendar.MILLISECOND, 0)
        time
    }

    fun goToPreviousDate() = with(Calendar.getInstance()) {
        time = date.value!!;add(Calendar.DATE, -1)
        date.value = time
    }

    fun goToNextDate() = with(Calendar.getInstance()) {
        time = date.value!!;add(Calendar.DATE, +1)
        date.value = time
    }

}