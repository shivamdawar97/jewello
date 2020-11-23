package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items" )
data class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0L,

    @ColumnInfo(name = "polish_charge")
    val polishCharge:Float = 0f,

    @ColumnInfo
    val labour:Int = 0

)