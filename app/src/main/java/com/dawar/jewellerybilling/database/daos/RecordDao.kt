package com.dawar.jewellerybilling.database.daos

import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Record

@Dao
interface RecordDao {

    @Insert
    suspend fun insert(record: Record)

    @Update
    fun update(record: Record)

    @Query("select * from records where recordId=:id")
    fun get(id: Long): Record?

    @Query("select * from records where customer_id=:id")
    suspend fun getRecordByCustomerId(id: Long): List<Record>

    @Query("select * from records where bill_id=:id")
    suspend fun getRecordByBillId(id: Long): List<Record>

    @Query("select * from records")
    fun getAll(): List<Record>?

    @Delete
    fun delete(record: Record)

    @Query("delete from records")
    fun clear()


}