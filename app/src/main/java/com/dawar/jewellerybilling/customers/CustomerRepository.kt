package com.dawar.jewellerybilling.customers

import com.dawar.jewellerybilling.database.daos.CustomerDao
import com.dawar.jewellerybilling.database.entities.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerRepository(private val customerDao: CustomerDao) {

    suspend fun addCustomer(customer:Customer) = withContext(Dispatchers.IO){
         customerDao.insert(customer)
    }
}