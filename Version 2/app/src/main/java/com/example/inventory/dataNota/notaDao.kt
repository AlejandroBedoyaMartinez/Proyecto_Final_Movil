package com.example.inventory.dataNota

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface notaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notaEntity: notaEntity)

    @Update
    suspend fun update(notaEntity: notaEntity)

    @Delete
    suspend fun delete(notaEntity: notaEntity)


    @Query("SELECT*FROM notaEntity")
     fun getAllNotes():kotlinx.coroutines.flow.Flow<List<notaEntity>>

    @Query("SELECT * from notaEntity WHERE id = :id")
    fun getNote(id: Int): Flow<notaEntity>
}