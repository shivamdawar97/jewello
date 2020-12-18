package com.dawar.jewellerybilling

import android.content.Context
import com.dawar.jewellerybilling.billing.BillingRepository
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.JewelloDatabase
import com.dawar.jewellerybilling.database.daos.BillDao
import com.dawar.jewellerybilling.database.daos.CustomerDao
import com.dawar.jewellerybilling.database.daos.ItemDao
import com.dawar.jewellerybilling.items.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class) //dependencies provided via this module shall stay alive as long as application is running.
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext app: Context) = JewelloDatabase.getInstance(app)

    @Singleton
    @Provides
    fun getItemDao(database: JewelloDatabase) = database.itemsDao

    @Singleton
    @Provides
    fun getBillDao(database: JewelloDatabase) = database.billDao

    @Singleton
    @Provides
    fun getCustomerDao(database: JewelloDatabase) = database.customerDao

    @Singleton
    @Provides
    fun getRecordDao(database: JewelloDatabase) = database.recordDao

    @Singleton
    @Provides
    fun getBillingRepository(itemsDao: ItemDao,billDao: BillDao,customerDao: CustomerDao) = BillingRepository(itemsDao,billDao,customerDao)

    @Singleton
    @Provides
    fun getCustomerRepository(customerDao: CustomerDao) = CustomerRepository(customerDao)

    @Singleton
    @Provides
    fun getItemsRepository(itemsDao: ItemDao) = ItemsRepository(itemsDao)
}