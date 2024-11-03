package jano.net.proyecto_final.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class nota(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val titulo:String,
    val descripcion:String,
    val cuerpo:String,
    val texto:String,
)
