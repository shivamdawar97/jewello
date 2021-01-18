package com.dawar.jewellerybilling.items.addItem

import android.util.Log
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.database.entities.Item
import com.dawar.jewellerybilling.items.ItemsRepository
import com.dawar.jewellerybilling.rx.RxBus
import com.dawar.jewellerybilling.rx.RxEvent
import kotlinx.coroutines.launch

class AddItemViewModel @ViewModelInject constructor(
    private val repository: ItemsRepository
): ViewModel() {

    var itemId = 0L // used only when item has to be updated
    val name = MutableLiveData<String>().also { it.value ="" }
    val polish = MutableLiveData<String>().also { it.value ="0.0" }
    val labour = MutableLiveData<String>().also { it.value ="0" }
    val isGold = MutableLiveData<Boolean>().also { it.value = true }

    val valid = MediatorLiveData<Boolean>().apply {
        addSource(name){ isFormValid() }
        addSource(polish){ isFormValid() }
        addSource(labour){ isFormValid() }
    }

    private fun isFormValid() {
        valid.value = name.value?.length!! > 3 && polish.value!!.isNotBlank() && labour.value!!.isNotBlank()
    }

    fun addItem() = viewModelScope.launch{
        val newItem = generateItem()
        repository.addItem(newItem)
        RxBus.publish(RxEvent.ItemAdded())
    }

    fun update() = viewModelScope.launch {
        val updateItem = generateItem(itemId)
        repository.updateItem(updateItem)
        RxBus.publish(RxEvent.ItemAdded())
    }

    private fun generateItem(id:Long=0L) =
        Item(id,name.value!!,isGold.value!!,polish.value!!.toFloat(),labour.value!!.toInt())

    fun populate(item: Item) {
        itemId = item.itemId
        name.value = item.name
        polish.value = item.polishCharge.toString()
        labour.value = item.labour.toString()
        isGold.value = item.isGold
    }

}