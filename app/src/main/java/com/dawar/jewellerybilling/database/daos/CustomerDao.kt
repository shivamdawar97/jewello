package com.dawar.jewellerybilling.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Customer

@Dao
interface CustomerDao {

    @Insert
    suspend fun insert(customer: Customer):Long

    @Update
    suspend fun update(customer: Customer)

    @Query("select * from customers where customerId=:id")
    suspend fun get(id: Long): Customer?

    @Query("select * from customers")
    fun getAll(): LiveData<List<Customer>>

    @Delete
    suspend fun delete(customer: Customer)

    @Query("delete from customers")
    fun clear()

    @Query("delete from customers where customerId in (:ids)")
    fun deleteSelected(ids:List<Long>)

}