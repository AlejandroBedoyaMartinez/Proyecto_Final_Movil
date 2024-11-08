package com.example.inventory.dataNota

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "notaEntity")
data class notaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo:String,
    val descripcion:String,
    val cuerpo:String,
    val texto:String,
)
