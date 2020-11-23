package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "items" )
data class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0L,

    @ColumnInfo
    val name: String,

    @ColumnInfo(name = "polish_charge")
    val polishCharge:Float = 0f,

    @ColumnInfo
    val labour:Int = 0,

    @Ignore
    val totalAmount: Int = 0

)