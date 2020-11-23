package com.dawar.jewellerybilling.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dawar.jewellerybilling.database.daos.ItemDao
import com.dawar.jewellerybilling.database.entities.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class JewelloDatabase : RoomDatabase() {

    abstract val itemsDao: ItemDao

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