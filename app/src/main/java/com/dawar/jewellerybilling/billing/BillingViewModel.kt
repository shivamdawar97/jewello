package com.dawar.jewellerybilling.billing

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.database.JewelloDatabase
import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.daos.ItemDao
import com.dawar.jewellerybilling.database.entities.Item
import kotlinx.coroutines.launch

class BillingViewModel(context: Context) : ViewModel() {

    private val _editRateEnabled = MutableLiveData<Boolean>()
    private val _goldRate = MutableLiveData<Float>()
    private val _silverRate = MutableLiveData<Float>()
    private val _items = MutableLiveData<List<Item>>()

    private lateinit var database : JewelloDatabase
    private lateinit var itemsDao : ItemDao
    private lateinit var billDao : BillDao

    val editRateEnabled: LiveData<Boolean>
        get() = _editRateEnabled

    val goldRate: LiveData<Float>
        get() = _goldRate

    val silverRate: LiveData<Float>
        get() = _silverRate

    val items: LiveData<List<Item>>
        get() = _items

    val lastBillNo = MutableLiveData<Int>()

    init {
        _editRateEnabled.value = false
        _goldRate.value = 0f
        _silverRate.value = 0f
        database = JewelloDatabase.getInstance(context)
        itemsDao = database.itemsDao
        billDao = database.billDao
        initializeDataSource()
    }

    private fun initializeDataSource() = viewModelScope.launch {
        // No need to specify the Dispatcher, Room uses Dispatchers.IO.
        _items.value = itemsDao.getAll()
        lastBillNo.value = billDao.getLastId().toInt() + 1
    }

    fun editRateEnabled() {
        _editRateEnabled.value = true
    }

    fun applyRates(goldRate: String?, silverRate: String?) {
        _goldRate.value = (goldRate ?: "0").toFloat()
        _silverRate.value = (silverRate ?: "0").toFloat()
        _editRateEnabled.value = false
    }

}