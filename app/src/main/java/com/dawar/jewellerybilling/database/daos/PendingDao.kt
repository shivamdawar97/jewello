package com.dawar.jewellerybilling.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Bill
import com.dawar.jewellerybilling.database.entities.Pending

@Dao
interface PendingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bill: Pending): Long

    @Query("select * from pending")
    fun getAll(): LiveData<List<Pending>>

    @Delete
    suspend fun delete(bill: Pending)

    @Query("delete from pending")
    suspend fun clear()
}