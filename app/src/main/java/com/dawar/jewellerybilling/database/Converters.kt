package com.dawar.jewellerybilling.database

import androidx.room.TypeConverter
import com.dawar.jewellerybilling.database.entities.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromString(value: String?): List<Item>? {
        val listType = object : TypeToken<List<Item>>() {}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun fromList(list: List<Item>?): String? {
        return Gson().toJson(list)
    }

}