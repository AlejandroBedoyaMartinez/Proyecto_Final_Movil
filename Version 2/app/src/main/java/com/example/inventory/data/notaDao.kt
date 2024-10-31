package com.example.inventorydata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface notaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: notaEntity)

    @Update
    suspend fun update(item: notaEntity)

    @Delete
    suspend fun delete(item: notaEntity)

    @Query("SELECT*FROM notaEntity")
    suspend fun getAllNotes():List<notaEntity>
}