package com.dawar.jewellerybilling.pendings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.database.daos.PendingDao
import com.dawar.jewellerybilling.database.entities.Pending
import kotlinx.coroutines.launch

class PendingViewModel @ViewModelInject constructor(private val pendingDao: PendingDao)
    :ViewModel() {

    val pending = pendingDao.getAll()

    fun savePending(pending: Pending) = viewModelScope.launch {
        pendingDao.insert(pending)
    }

    fun delete(pending:Pending) = viewModelScope.launch {
        pendingDao.delete(pending)
    }

    fun deleteAll() = viewModelScope.launch {
        pendingDao.clear()
    }

}