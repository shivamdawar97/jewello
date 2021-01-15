package com.dawar.jewellerybilling.database.daos

import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Bill

@Dao
interface BillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bill: Bill): Long

    @Update
    fun update(bill: Bill)

    @Query("select * from bills where billId=:id")
    suspend fun getBill(id: Long): Bill?

    @Query("select * from bills where date between :dayStart and :dayEnd ORDER By date DESC")
    suspend fun billsByDate(dayStart:Long,dayEnd:Long): List<Bill>

    @Delete
    suspend fun delete(bill: Bill)

    @Query("delete from bills")
    fun clear()

    @Query("SELECT billId FROM bills ORDER BY date DESC LIMIT 1")
    suspend fun getLastId(): Long?

    @Query("select count(billId) from bills ")
    suspend fun getCount():Long
}