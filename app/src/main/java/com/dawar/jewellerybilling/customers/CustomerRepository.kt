package com.dawar.jewellerybilling.customers

import com.dawar.jewellerybilling.database.daos.CustomerDao
import com.dawar.jewellerybilling.database.entities.Customer
import com.dawar.jewellerybilling.database.entities.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton

@Singleton
class CustomerRepository(private val customerDao: CustomerDao) {

    suspend fun addCustomer(customer:Customer) = customerDao.insert(customer)
    suspend fun getCustomer(id:Long) = customerDao.get(id)

    fun getAllCustomers() = customerDao.getAll()
    suspend fun saveCustomer(customer: Customer) = customerDao.update(customer)
    suspend fun deleteCustomer(customer: Customer) = customerDao.delete(customer)

//    suspend fun getAllCustomers() = customerDao.getAll()

}