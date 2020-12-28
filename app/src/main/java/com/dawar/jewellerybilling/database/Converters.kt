package com.dawar.jewellerybilling.database

import androidx.room.TypeConverter
import com.dawar.jewellerybilling.billing.BillItem
import com.dawar.jewellerybilling.database.entities.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    @TypeConverter
    fun fromString(value: String?): List<BillItem>? {
        val listType = object : TypeToken<List<BillItem>>() {}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun fromList(list: List<BillItem>?): String? {
        return Gson().toJson(list)
    }

}