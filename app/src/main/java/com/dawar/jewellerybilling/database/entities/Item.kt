package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "items" )
data class Item(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,

    @ColumnInfo
    var name: String,

    @ColumnInfo(name = "polish_charge")
    var polishCharge:Float = 0f,

    @ColumnInfo
    var labour:Int = 0

)