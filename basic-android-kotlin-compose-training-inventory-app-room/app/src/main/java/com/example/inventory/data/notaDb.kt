package com.example.inventorydata

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [notaEntity::class],
    version = 1
)
abstract class notaDb:RoomDatabase(){
abstract val dao:notaDao
}