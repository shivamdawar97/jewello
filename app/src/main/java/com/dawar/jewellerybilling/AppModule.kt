package com.dawar.jewellerybilling

import android.content.Context
import androidx.datastore.preferences.createDataStore
import com.dawar.jewellerybilling.billing.BillingRepository
import com.dawar.jewellerybilling.customers.CustomerRepository
import com.dawar.jewellerybilling.database.JewelloDatabase
import com.dawar.jewellerybilling.database.daos.*
import com.dawar.jewellerybilling.items.ItemsRepository
import com.dawar.jewellerybilling.records.RecordRepository
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
    fun provideDataPreferences(@ApplicationContext app: Context) = app.createDataStore("jewello")

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) = app.getSharedPreferences("jewello",Context.MODE_PRIVATE)

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
    fun getPendingDao(database: JewelloDatabase) = database.pendingDao

    @Singleton
    @Provides
    fun getBillingRepository(itemsDao: ItemDao,billDao: BillDao,customerDao: CustomerDao,recordDao: RecordDao,pendingDao: PendingDao) =
        BillingRepository(itemsDao,billDao,customerDao,recordDao,pendingDao)

    @Singleton
    @Provides
    fun getCustomerRepository(customerDao: CustomerDao) = CustomerRepository(customerDao)

    @Singleton
    @Provides
    fun getItemsRepository(itemsDao: ItemDao) = ItemsRepository(itemsDao)

    @Singleton
    @Provides
    fun getRecordsRepository(recordDao: RecordDao) = RecordRepository(recordDao)

}