package com.dawar.jewellerybilling.database.daos

import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Customer

@Dao
interface CustomerDao {

    @Insert
    fun insert(customer: Customer)

    @Update
    fun update(customer: Customer)

    @Query("select * from customers where customerId=:id")
    fun get(id: Long): Customer?

    @Query("select * from customers")
    fun getAll(): List<Customer>?

    @Delete
    fun delete(customer: Customer)

    @Query("delete from customers")
    fun clear()

}