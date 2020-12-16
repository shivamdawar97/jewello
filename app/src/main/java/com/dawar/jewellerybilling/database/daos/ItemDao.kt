package com.dawar.jewellerybilling.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dawar.jewellerybilling.database.entities.Item

@Dao
interface ItemDao {

    @Insert
    fun insert(item:Item)

    @Update
    fun update(item: Item)

    @Query("select * from items where itemId=:id")
    fun get(id: Long): Item?

    @Query("select * from items")
    fun getAll(): LiveData<List<Item>>

    @Delete
    fun delete(item: Item)

    @Query("delete from items")
    fun clear()

}