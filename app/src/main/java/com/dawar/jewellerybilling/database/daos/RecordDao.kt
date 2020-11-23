package com.dawar.jewellerybilling.database.daos

import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Record

@Dao
interface RecordDao {

    @Insert
    fun insert(record: Record)

    @Update
    fun update(record: Record)

    @Query("select * from records where recordId=:id")
    fun get(id: Long): Record?

    @Query("select * from records")
    fun getAll(): List<Record>?

    @Delete
    fun delete(record: Record)

    @Query("delete from records")
    fun clear()

}