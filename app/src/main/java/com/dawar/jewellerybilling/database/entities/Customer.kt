package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "customers")
data class Customer(

    @PrimaryKey(autoGenerate = true)
    var customerId: Long = 0L,

    @ColumnInfo
    var name: String,

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String = "N/A",

    @ColumnInfo
    var email: String = "N/A",

    @ColumnInfo
    var address: String = "N/A",

    @ColumnInfo
    var balance: Int = 0

):Serializable