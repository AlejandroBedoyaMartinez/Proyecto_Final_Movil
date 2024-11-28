package com.example.inventory.dataTarea

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareaEntity")
data class tareaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo:String,
    val descripcion:String,
    val cuerpo:String,
    val texto:String,
    val fechaIncio:String,
    val fechaFin:String,
    val recordar:Boolean,
    val hecho:Boolean,
    val imagenes: List<String> = emptyList(),
    val videos: List<String> = emptyList()
)
