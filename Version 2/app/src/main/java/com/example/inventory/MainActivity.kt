package com.example.inventory

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.app.NotificationCompat
import androidx.room.Room
import androidx.work.WorkManager
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



    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val dbNota= Room.databaseBuilder(this, notaDb::class.java,"nota_db").build()
        val daoNota=dbNota.dao
        val repositoryNota = notaRepository(daoNota)
        val viewModelNota = viewModelNota(repositoryNota)

        val dbTarea= Room.databaseBuilder(this, tareaDb::class.java,"tarea_db").build()
        val daoTarea=dbTarea.tareaDao()
        val repositoryTarea = tareaRepository(daoTarea)
        val viewModelTarea = ViewModelTarea(repositoryTarea)
        enableEdgeToEdge()
        setContent {
            Proyecto_FinalTheme {
                val windowSize = calculateWindowSizeClass(this)
                    Nav(viewModelNota,viewModelTarea, windowSize = windowSize.widthSizeClass)
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


        //configurando notificacion
            //en el viewmodel de tarea y en un archivo llamado NotificationManager.kt y ViewModelTarea
        }


}
