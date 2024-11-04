package com.example.inventory.dataTarea

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface tareaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tareaEntity: tareaEntity)

    @Update
    suspend fun update(tareaEntity: tareaEntity)

    @Delete
    suspend fun delete(tareaEntity: tareaEntity)


    @Query("SELECT*FROM tareaEntity")
    fun getAllTareas():kotlinx.coroutines.flow.Flow<List<tareaEntity>>

    @Query("SELECT * from tareaEntity WHERE id = :id")
    fun getTarea(id: Int): Flow<tareaEntity>
}