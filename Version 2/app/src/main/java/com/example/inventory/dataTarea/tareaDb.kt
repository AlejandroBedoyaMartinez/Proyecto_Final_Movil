package com.example.inventory.dataTarea

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [tareaEntity::class],
    version = 1
)
abstract class tareaDb:RoomDatabase() {
    abstract val dao:tareaDao
}