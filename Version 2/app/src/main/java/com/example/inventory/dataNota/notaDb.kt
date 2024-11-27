package com.example.inventory.dataNota

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [notaEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class notaDb:RoomDatabase(){
abstract val dao: notaDao
}