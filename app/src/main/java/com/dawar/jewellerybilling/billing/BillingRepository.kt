package com.dawar.jewellerybilling.billing

import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.daos.CustomerDao
import com.dawar.jewellerybilling.database.daos.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository constructor(private val itemDao: ItemDao, private val billDao: BillDao, private val customerDao: CustomerDao) {

    // No need to specify the Dispatcher, Room uses Dispatchers.IO.
    fun getAllItems() =  itemDao.getAll()

    fun getAllCustomers() = customerDao.getAll()

    suspend fun getLastBillId() = withContext(Dispatchers.IO) { return@withContext billDao.getLastId().toInt() }

}