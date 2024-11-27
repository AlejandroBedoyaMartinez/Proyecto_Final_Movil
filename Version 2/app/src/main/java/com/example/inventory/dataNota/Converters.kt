package com.example.inventory.dataNota

import androidx.room.TypeConverter
class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split(",") ?: emptyList()
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}
