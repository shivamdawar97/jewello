package com.dawar.jewellerybilling.billing

import androidx.lifecycle.MutableLiveData
import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.daos.CustomerDao
import com.dawar.jewellerybilling.database.daos.ItemDao
import com.dawar.jewellerybilling.database.daos.RecordDao
import com.dawar.jewellerybilling.database.entities.Bill
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.database.entities.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository constructor(private val itemDao: ItemDao,
                                    private val billDao: BillDao,
                                    private val customerDao: CustomerDao,
                                    private val recordDao: RecordDao) {

    // No need to specify the Dispatcher, Room uses Dispatchers.IO.
    fun getAllItems() =  itemDao.getAll()

    fun getAllCustomers() = customerDao.getAll()

    suspend fun getLastBillId() =  billDao.getLastId()?.toInt()?:0

    suspend fun saveBill(newBill: Bill) = billDao.insert(newBill)

    suspend fun saveRecord(newRecord: Record) {
        recordDao.insert(newRecord)
    }

    suspend fun getBillById(billId:Long) = billDao.get(billId)

    suspend fun saveCustomer(customer: Customer) = customerDao.update(customer)

    fun savePending(pendingBill: Bill) {

    }


}