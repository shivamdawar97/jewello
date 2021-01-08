package com.dawar.jewellerybilling.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dawar.jewellerybilling.billing.BillItem

@Entity(tableName = "pending")
data class Pending (

    @PrimaryKey(autoGenerate = true)
    var pendingId: Long = 0L,

    @ColumnInfo
    val items: List<BillItem>,

    @ColumnInfo(name = "customer_id")
    val customerId: Long,

    @ColumnInfo(name = "customer_name")
    val customerName: String,

    @ColumnInfo(name = "amount_received")
    val amountReceived: Int,

)