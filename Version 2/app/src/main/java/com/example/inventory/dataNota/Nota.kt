package com.example.inventory.dataNota

import androidx.room.PrimaryKey

data class Nota (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val titulo:String,
    val descripcion:String,
    val cuerpo:String,
    val texto:String,
    val imagenes: List<String> = emptyList()

)