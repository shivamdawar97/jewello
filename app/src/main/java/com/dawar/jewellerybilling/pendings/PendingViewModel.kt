package com.dawar.jewellerybilling.pendings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.database.daos.PendingDao
import com.dawar.jewellerybilling.database.entities.Pending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PendingViewModel @ViewModelInject constructor(private val pendingDao: PendingDao)
    :ViewModel() {

    var pending = pendingDao.getAll()

    fun delete(pending:Pending) = viewModelScope.launch {
        pendingDao.delete(pending)
    }

    fun deleteAll() = viewModelScope.launch {
        pendingDao.clear()
    }

}