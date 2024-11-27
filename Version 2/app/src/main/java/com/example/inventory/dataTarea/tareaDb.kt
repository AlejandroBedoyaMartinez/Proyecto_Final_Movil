package com.example.inventory.dataTarea

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.inventory.dataNota.Converters

@Database(
    entities = [tareaEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class tareaDb:RoomDatabase() {
    abstract val dao:tareaDao
}