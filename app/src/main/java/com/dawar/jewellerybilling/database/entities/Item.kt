package com.dawar.jewellerybilling.database.entities

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "items",indices = [Index(value = ["name"],unique = true)])
data class Item(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var isGold: Boolean,

    @ColumnInfo(name = "polish_charge")
    var polishCharge:Float = 0f,

    @ColumnInfo
    var labour:Int = 0

):Serializable