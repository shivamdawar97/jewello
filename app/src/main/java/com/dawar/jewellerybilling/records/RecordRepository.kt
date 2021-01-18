package com.dawar.jewellerybilling.records

import com.dawar.jewellerybilling.database.daos.RecordDao
import com.dawar.jewellerybilling.database.entities.Record
import javax.inject.Singleton

@Singleton
class RecordRepository(private val recordDao: RecordDao) {

    fun getRecordByCustomerId(id:Long) = recordDao.getRecordByCustomerId(id)
    suspend fun saveRecord(newRecord: Record) = recordDao.insert(newRecord)
    suspend fun deleteCustomerRecords(id: Long) = recordDao.deleteById(id)

}