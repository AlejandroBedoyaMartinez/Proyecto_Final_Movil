package com.example.inventory.ui.nota

import androidx.room.PrimaryKey

data class Nota (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val titulo:String,
    val descripcion:String,
    val cuerpo:String,
    val texto:String
)