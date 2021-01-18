package com.dawar.jewellerybilling.items

import com.dawar.jewellerybilling.database.daos.ItemDao
import com.dawar.jewellerybilling.database.entities.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton

@Singleton
class ItemsRepository(private val itemsDao: ItemDao) {
    suspend fun addItem(item: Item) = itemsDao.insert(item)
    fun getAllItems() = itemsDao.getAll()
    suspend fun updateItem(updateItem: Item) = itemsDao.update(updateItem)
}