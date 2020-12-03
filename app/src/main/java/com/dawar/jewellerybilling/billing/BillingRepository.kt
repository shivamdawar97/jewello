package com.dawar.jewellerybilling.billing

import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.daos.ItemDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository @Inject constructor(private val itemDao: ItemDao, private val billDao: BillDao) {

    // No need to specify the Dispatcher, Room uses Dispatchers.IO.
    suspend fun getAllItems() = itemDao.getAll()

    suspend fun getLastBillId() = billDao.getLastId().toInt()


}