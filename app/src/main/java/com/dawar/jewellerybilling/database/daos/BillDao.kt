package com.dawar.jewellerybilling.database.daos

import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Bill

@Dao
interface BillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: Bill)

    @Update
    fun update(bill: Bill)

    @Query("select * from bills where billId=:id")
    fun get(id: Long): Bill?

    @Query("select * from bills")
    fun getAll(): List<Bill>?

    @Delete
    fun delete(bill: Bill)

    @Query("delete from bills")
    fun clear()

    @Query("SELECT billId FROM bills ORDER BY date DESC LIMIT 1")
    fun getLastId(): Long
}
