package com.dawar.jewellerybilling.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dawar.jewellerybilling.database.daos.*
import com.dawar.jewellerybilling.database.entities.*
import javax.inject.Singleton

@Singleton
@Database(entities = [Bill::class,Customer::class,Item::class,Record::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JewelloDatabase : RoomDatabase() {

    abstract val itemsDao: ItemDao
    abstract val customerDao: CustomerDao
    abstract val billDao: BillDao
    abstract val recordDao: RecordDao

    companion object {

        @Volatile
        private var INSTANCE: JewelloDatabase? = null

        fun getInstance(context: Context):JewelloDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JewelloDatabase::class.java,
                        "jewello_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}