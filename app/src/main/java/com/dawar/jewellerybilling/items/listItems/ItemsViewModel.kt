package com.dawar.jewellerybilling.items.listItems

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawar.jewellerybilling.database.entities.Item
import com.dawar.jewellerybilling.items.ItemsRepository
import kotlinx.coroutines.launch

class ItemsViewModel @ViewModelInject constructor(repository: ItemsRepository): ViewModel() {
    val items = repository.getAllItems()
}