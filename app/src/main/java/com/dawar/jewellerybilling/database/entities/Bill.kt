package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "bills")
data class Bill(

    @PrimaryKey(autoGenerate = true,)
    var billId: Long = 0L,

    @ColumnInfo(name = "gold_rate")
    var goldRate:Int = 0,

    @ColumnInfo(name = "silver_rate")
    var silverRate:Int = 0,

    @ColumnInfo
    val items : List<Item>,

    @ColumnInfo(name = "customer_id")
    val customerId: Long,

    @ColumnInfo
    val date : Date,

    @ColumnInfo(name = "total_amount")
    val totalAmount : Int,

    @ColumnInfo(name = "amount_received")
    val amountReceived : Int,

    @ColumnInfo(name = "balance_amount")
    val balanceAmount : Int,
)