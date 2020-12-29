package com.dawar.jewellerybilling.records

import com.dawar.jewellerybilling.database.daos.RecordDao
import javax.inject.Singleton

@Singleton
class RecordRepository(private val recordDao: RecordDao) {

    suspend fun getRecordByCustomerId(id:Long) = recordDao.getRecordByCustomerId(id)
    suspend fun getRecordByBillId(id:Long) = recordDao.getRecordByBillId(id)

}