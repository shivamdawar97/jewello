package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "records")
data class Record (

    @PrimaryKey(autoGenerate = true)
    var recordId: Long =0L,

    @ColumnInfo
    var date : Long,

    @ColumnInfo
    var amount: Int = 0,

    @ColumnInfo(name = "bill_id")
    var billId: Long = 0L,

    @ColumnInfo(name = "customer_id")
    var customerId: Long = 0L

)