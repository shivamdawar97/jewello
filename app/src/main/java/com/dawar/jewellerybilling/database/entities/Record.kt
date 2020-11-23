package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "records")
data class Record (

    @PrimaryKey(autoGenerate = true)
    val recordId: Long =0L,

    @ColumnInfo
    val date : Date,

    @ColumnInfo
    val amount: Int = 0,

    @ColumnInfo(name = "bill_id")
    val billId: Long = 0L,

    @ColumnInfo(name = "customer_id")
    val customerId: Long = 0L

)