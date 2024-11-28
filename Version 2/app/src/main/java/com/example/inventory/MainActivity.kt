package com.example.inventory

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.inventory.ui.theme.Proyecto_FinalTheme
import com.example.inventory.dataNota.notaDb
import com.example.inventory.dataNota.notaRepository
import com.example.inventory.dataTarea.tareaDb
import com.example.inventory.dataTarea.tareaRepository
import com.example.inventory.ui.nota.viewModelNota
import com.example.inventory.ui.tarea.ViewModelTarea

class MainActivity : ComponentActivity() {
    //crear el canal
    val channelID = "tareas_channel"
    val channelName = "Notificaciones de Tareas"

    // Launcher para solicitar permisos
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // Informar al usuario que el permiso es necesario
            Toast.makeText(this, "Permiso para notificaciones es requerido, para avisarte >:( .", Toast.LENGTH_SHORT).show()
        }
    }



    // Verificar y solicitar el permiso de notificaciones
    fun checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar el permiso
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        checkNotificationPermission()

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
            val viewModelTarea = remember { ViewModelTarea(repositoryTarea) }
            Proyecto_FinalTheme {
                Nav(viewModelNota,viewModelTarea)
            }


        }

        //construir el canal
        val importancia = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, channelName, importancia).apply {
            description = "Notificaciones para tareas próximas a vencer"
        }

        //manager de notificaciones
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)



    }
}
