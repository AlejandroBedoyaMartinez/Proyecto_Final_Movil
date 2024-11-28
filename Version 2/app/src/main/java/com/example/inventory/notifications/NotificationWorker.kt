package com.example.inventory.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.inventory.R
import java.util.Calendar
import java.util.concurrent.TimeUnit


class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val titulo = inputData.getString("titulo") ?: "Recordatorio"
        val mensaje = inputData.getString("mensaje") ?: "Tienes tareas pendientes."

        // Verificar si el permiso de notificaciones está concedido
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure() // No se puede mostrar la notificación sin permiso
        }

        // Mostrar la notificación
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val notificationId = System.currentTimeMillis().toInt()

        val notification = NotificationCompat.Builder(applicationContext, "tareas_channel")
            .setSmallIcon(R.drawable.troste) // Asegúrate de tener un ícono válido
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setStyle(NotificationCompat.BigTextStyle().bigText(mensaje))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notificationId, notification)

        return Result.success()
    }

}
