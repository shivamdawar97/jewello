package com.dawar.jewellerybilling.billing

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.database.JewelloDatabase
import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.daos.ItemDao
import com.dawar.jewellerybilling.database.entities.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class BillingViewModel(context: Context) : ViewModel() {

    private val _editRateEnabled = MutableLiveData<Boolean>()
    private val _goldRate = MutableLiveData<Float>()
    private val _silverRate = MutableLiveData<Float>()
    private val _items = MutableLiveData<List<Item>>()

    private var database: JewelloDatabase
    private var itemsDao: ItemDao
    private var billDao: BillDao

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
        _items.value = getAllItems()
        lastBillNo.value = getLastBillId() + 1
    }

    // No need to specify the Dispatcher, Room uses Dispatchers.IO.
    private suspend fun getAllItems() = withContext(Dispatchers.IO){
        return@withContext itemsDao.getAll()
    }

    private suspend fun getLastBillId() = withContext(Dispatchers.IO){
        return@withContext billDao.getLastId().toInt()
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

