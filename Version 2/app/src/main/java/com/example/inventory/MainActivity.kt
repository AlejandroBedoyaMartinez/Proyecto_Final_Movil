package com.example.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.inventory.ui.theme.Proyecto_FinalTheme
import com.example.inventory.dataNota.notaDb
import com.example.inventory.dataNota.notaRepository
import com.example.inventory.dataTarea.tareaDb
import com.example.inventory.dataTarea.tareaRepository
import com.example.inventory.ui.nota.viewModelNota
import com.example.inventory.ui.tarea.ViewModelTarea


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbNota= Room.databaseBuilder(this, notaDb::class.java,"nota_db").build()
        val daoNota=dbNota.dao
        val repositoryNota = notaRepository(daoNota)
        val viewModelNota = viewModelNota(repositoryNota)

        val dbTarea= Room.databaseBuilder(this, tareaDb::class.java,"tarea_db").build()
        val daoTarea=dbTarea.dao
        val repositoryTarea = tareaRepository(daoTarea)
        val viewModelTarea = ViewModelTarea(repositoryTarea)
        enableEdgeToEdge()
        setContent {
            Proyecto_FinalTheme {
                    Nav(viewModelNota,viewModelTarea)
                }
            }
        }
    }
