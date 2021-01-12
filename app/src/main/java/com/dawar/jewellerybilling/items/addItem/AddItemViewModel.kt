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
        val newItem = Item(0,name.value!!,isGold.value!!,polish.value!!.toFloat(),labour.value!!.toInt())
        repository.addItem(newItem)
        RxBus.publish(RxEvent.ItemAdded())
    }



}