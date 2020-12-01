package com.dawar.jewellerybilling.billing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.daos.ItemDao
import com.dawar.jewellerybilling.database.entities.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BillingViewModel @ViewModelInject constructor (private val itemDao: ItemDao, private val billDao: BillDao) : ViewModel() {

    private val _editRateEnabled = MutableLiveData<Boolean>()
    private val _goldRate = MutableLiveData<Float>()
    private val _silverRate = MutableLiveData<Float>()
    private val _items = MutableLiveData<List<Item>>()


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
        initializeDataSource()
    }

    private fun initializeDataSource() = viewModelScope.launch {
        _items.value = getAllItems()
        lastBillNo.value = getLastBillId() + 1
    }

    // No need to specify the Dispatcher, Room uses Dispatchers.IO.
    private suspend fun getAllItems() = withContext(Dispatchers.IO){
        return@withContext itemDao.getAll()
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

