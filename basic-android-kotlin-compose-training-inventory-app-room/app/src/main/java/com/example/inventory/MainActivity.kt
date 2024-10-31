package com.example.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.inventory.ui.theme.Proyecto_FinalTheme
import com.example.inventorydata.notaDb
import com.example.inventorydata.notaRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db= Room.databaseBuilder(this, notaDb::class.java,"nota_db").build()
    val dao=db.dao
        val repositoryNota =notaRepository(dao)
        val viewModel = viewModel(repositoryNota)
        enableEdgeToEdge()
        setContent {
            Proyecto_FinalTheme {
                    Nav(viewModel)
                }
            }
        }
    }
