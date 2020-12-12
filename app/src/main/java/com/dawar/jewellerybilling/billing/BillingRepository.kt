package com.dawar.jewellerybilling.billing

import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.daos.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository constructor(private val itemDao: ItemDao, private val billDao: BillDao) {

    // No need to specify the Dispatcher, Room uses Dispatchers.IO.
    suspend fun getAllItems() = withContext(Dispatchers.IO) { return@withContext itemDao.getAll() }

    suspend fun getLastBillId() = withContext(Dispatchers.IO) { return@withContext billDao.getLastId().toInt() }


}