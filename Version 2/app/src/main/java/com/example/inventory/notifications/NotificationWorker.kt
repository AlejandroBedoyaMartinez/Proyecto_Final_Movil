package com.example.inventory.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.inventory.R
import com.example.inventory.dataTarea.tareaDb
import java.util.Calendar

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val tipo = inputData.getString("tipo") ?: ""

        return try {
            if (tipo == "recordatorio_diario") {
                enviarNotificacionesDiarias()
            } else {
                val titulo = inputData.getString("titulo") ?: "Tarea"
                val mensaje = inputData.getString("mensaje") ?: "Nueva notificación"
                mostrarNotificacion(titulo, mensaje)
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }




    private fun enviarNotificacionesDiarias() {

        val tareaDb = Room.databaseBuilder(
            applicationContext,
            tareaDb::class.java,
            "tareaDb"
        ).build()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // Inicio y fin del día actual
        val inicioDelDia = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val finDelDia = calendar.timeInMillis

        // Obtener las tareas desde el DAO
        val tareasHoy = tareaDb.tareaDao().obtenerTareasVencenHoy(inicioDelDia, finDelDia)

        for (tarea in tareasHoy) {
            mostrarNotificacion(
                "Tarea vence hoy",
                "La tarea '${tarea.titulo}' vence hoy a las 5 PM."
            )
        }
    }

    private fun mostrarNotificacion(titulo: String, mensaje: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = System.currentTimeMillis().toInt()

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "tareas_channel")
            .setSmallIcon(R.drawable.troste)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "tareas_channel",
                "Recordatorios de Tareas",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}