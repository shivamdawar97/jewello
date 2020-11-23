package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(

    @PrimaryKey(autoGenerate = true)
    val customerId: Long = 0L,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String = "N/A",

    @ColumnInfo
    val email: String = "N/A",

    @ColumnInfo
    val address: String = "N/A",




)