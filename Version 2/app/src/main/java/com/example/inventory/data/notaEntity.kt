package com.example.inventorydata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class notaEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val titulo:String,
    val descripcion:String,
    val cuerpo:String,
    val texto:String,
)
