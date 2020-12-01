package com.dawar.jewellerybilling

import android.content.Context
import com.dawar.jewellerybilling.database.JewelloDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) //dependencies provided via this module shall stay alive as long as application is running.
object AppModule {

    @Singleton
    @Provides
    fun initiateDatabase(@ApplicationContext app: Context) = JewelloDatabase.getInstance(app)

    @Singleton
    @Provides
    fun getItemDao(db: JewelloDatabase) = db.itemsDao

    @Singleton
    @Provides
    fun getBillDao(db: JewelloDatabase) = db.billDao

}