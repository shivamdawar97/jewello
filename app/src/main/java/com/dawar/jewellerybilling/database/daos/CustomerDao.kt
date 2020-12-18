package com.dawar.jewellerybilling.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Customer

@Dao
interface CustomerDao {

    @Insert
    suspend fun insert(customer: Customer)

    @Update
    fun update(customer: Customer)

    @Query("select * from customers where customerId=:id")
    fun get(id: Long): Customer?

    @Query("select * from customers")
    fun getAll(): LiveData<List<Customer>>

    @Delete
    fun delete(customer: Customer)

    @Query("delete from customers")
    fun clear()

}