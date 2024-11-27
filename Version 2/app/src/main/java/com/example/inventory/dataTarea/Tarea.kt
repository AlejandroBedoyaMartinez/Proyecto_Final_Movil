package com.example.inventory.dataTarea

import androidx.room.PrimaryKey

data class Tarea(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val titulo:String,
    val descripcion:String,
    val cuerpo:String,
    val texto:String,
    val fechaIncio:String,
    val fechaFin:String,
    val recordar:Boolean,
    var hecho:Boolean,
    val imagenes: List<String> = emptyList()
)
