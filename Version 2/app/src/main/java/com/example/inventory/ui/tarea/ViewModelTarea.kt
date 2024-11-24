package com.example.inventory.ui.tarea

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.inventory.dataTarea.Tarea
import com.example.inventory.dataTarea.tareaRepository
import com.example.inventory.notifications.NotificationWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class ViewModelTarea(
    private val tareaRepository: tareaRepository
) : ViewModel() {
    var query = mutableStateOf("")
    var titulo = mutableStateOf("")
    var descripcion = mutableStateOf("")
    var descripcionCuerpo = mutableStateOf("")
    var texto = mutableStateOf("")
    var fechaInicio = mutableStateOf("")
    var fechaFin = mutableStateOf("")
    var recordar = mutableStateOf(false)
    var hecho = mutableStateOf(false)
    private val _tareas: MutableStateFlow<List<Tarea>> = MutableStateFlow(emptyList())
    var tareas: StateFlow<List<Tarea>> = _tareas
    var tarea: Tarea = Tarea(0,"","","","","","",false,false)

    init {
        viewModelScope.launch {
            tareaRepository.getTareas().collect { tareaList ->
                _tareas.value = tareaList
            }
        }
        programarNotificacionDiaria()
    }

    fun getTareas(){
        viewModelScope.launch {
            tareaRepository.getTareas().collect { tareaList ->
                _tareas.value = tareaList
            }
        }
    }

    fun getTarea(id: Int) {
        viewModelScope.launch {
            val tareaActual = tareaRepository.getTarea(id).first()
            tareaActual?.let {
                titulo.value = it.titulo
                descripcion.value = it.descripcion
                descripcionCuerpo.value = it.cuerpo
                texto.value = it.texto
                fechaInicio.value = it.fechaIncio
                fechaFin.value = it.fechaFin
                recordar.value = it.recordar
                hecho.value = it.hecho
                tarea  = it
            }
        }
    }

    fun savedTarea() {
        val nuevaTarea = Tarea(
            titulo = titulo.value,
            descripcion = descripcion.value,
            cuerpo = descripcionCuerpo.value,
            texto = texto.value,
            fechaIncio = fechaInicio.value,
            fechaFin = fechaFin.value,
            recordar = recordar.value,
            hecho = false
        )
        viewModelScope.launch {
            tareaRepository.insertTarea(nuevaTarea)
            programarNotificacion(nuevaTarea) // Programar notificaciones regulares

            // Notificación inmediata si vence hoy
            val dueDateMillis = convertirFechaAMillis(nuevaTarea.fechaFin)
            val hoy = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(System.currentTimeMillis())
            val fechaTarea = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dueDateMillis ?: 0)

            if (nuevaTarea.recordar && hoy == fechaTarea) {
                programarWorker(
                    delay = 0, // Notificación inmediata
                    titulo = "Tarea para hoy",
                    mensaje = "La tarea '${nuevaTarea.titulo}' debe completarse hoy."
                )
            }
        }
    }


    fun programarWorker(delay: Long, titulo: String, mensaje: String) {
        val data = Data.Builder()
            .putString("titulo", titulo)
            .putString("mensaje", mensaje)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance().enqueue(workRequest)
    }


    fun editTarea()
    {
        val tareaActualizar= Tarea(
            id = tarea.id,
            titulo = titulo.value,
            descripcion = descripcion.value,
            cuerpo = descripcionCuerpo.value,
            texto = texto.value,
            fechaIncio = fechaInicio.value,
            fechaFin = fechaFin.value,
            recordar = recordar.value,
            hecho = hecho.value
        )
        viewModelScope.launch {
            tareaRepository.editTarea(tareaActualizar)
        }
    }

    fun editCheck(tareaActualizar:Tarea) {
        viewModelScope.launch {
            tareaRepository.editTarea(tareaActualizar)
        }
    }

    fun deleteTarea(tarea: Tarea)
    {
        viewModelScope.launch {
            tareaRepository.deleteTarea(tarea)
        }

    }

    fun tituloVacio(): Boolean {
        return if(titulo.value == "")false else true
    }

    fun llenarDatos(it: Tarea){
        titulo.value = it.titulo
        descripcion.value = it.descripcion
        descripcionCuerpo.value = it.cuerpo
        texto.value = it.texto
        fechaInicio.value = it.fechaIncio
        fechaFin.value = it.fechaFin
        recordar.value = it.recordar
    }

    fun limpiarVariables() {
        titulo.value = ""
        descripcion.value = ""
        descripcionCuerpo.value = ""
        texto.value = ""
        fechaInicio.value = ""
        fechaFin.value = ""
        recordar.value = false
        hecho.value = false
    }


    fun programarNotificacion(tarea: Tarea) {
        try {
            if (tarea.recordar && tarea.fechaFin.isNotEmpty()) {
                val dueDateMillis = convertirFechaAMillis(tarea.fechaFin) ?: return
                val now = System.currentTimeMillis()

                // Notificación 4 horas antes
                val cuatroHorasAntes = dueDateMillis - TimeUnit.HOURS.toMillis(4)
                if (cuatroHorasAntes > now) {
                    val delayCuatroHoras = cuatroHorasAntes - now
                    programarWorker(
                        delay = delayCuatroHoras,
                        titulo = "Tarea pendiente",
                        mensaje = "La tarea '${tarea.titulo}' se vence en 4 horas."
                    )
                }

                // Notificación para entregar hoy ya está en savedTarea
            }
        } catch (e: Exception) {
            Log.e("Notificacion", "Error al programar notificaciones", e)
        }
    }

    fun programarNotificacionDiaria() {
        val ahora = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = ahora
            set(Calendar.HOUR_OF_DAY, 17) // 5 PM
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis < ahora) {
            calendar.add(Calendar.DAY_OF_YEAR, 1) // Programar para mañana si ya pasó hoy
        }

        val delay = calendar.timeInMillis - ahora
        val data = Data.Builder()
            .putString("tipo", "recordatorio_diario")
            .build()

        val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance().enqueue(notificationWorkRequest)
    }



    fun convertirFechaAMillis(fecha: String): Long? {
        return try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.parse(fecha)
            date?.time
        } catch (e: Exception) {
            Log.e("Notificacion", "Error al convertir fecha: $fecha", e)
            null
        }
    }

    //    fun programarNotificacion(tarea: Tarea) {
//        try {
//            Log.d("Notificacion", "Inicio de programarNotificacion")
//
//            if (tarea.recordar && tarea.fechaFin.isNotEmpty()) {
//                Log.d("Notificacion", "Tarea requiere recordatorio: ${tarea.titulo}")
//
//                val dueDateMillis = convertirFechaAMillis(tarea.fechaFin)
//                if (dueDateMillis == null) {
//                    Log.e("Notificacion", "Fecha inválida: ${tarea.fechaFin}")
//                    return
//                }
//
//                val delay = getDelayForNotification(dueDateMillis)
//                Log.d("Notificacion", "Delay calculado: $delay ms")
//
//                val data = Data.Builder()
//                    .putString("titulo", "Tarea cerca de la entrega")
//                    .putString("mensaje", "La tarea '${tarea.titulo}' vence pronto!.")
//                    .build()
//
//                // mostrar notificación inmediata que quiere decir que se vence el mismo dia
//                val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
//                    .setInitialDelay(if (delay > 0) delay else 0, TimeUnit.MILLISECONDS)
//                    .setInputData(data)
//                    .build()
//
//                WorkManager.getInstance().enqueue(notificationWorkRequest)
//                Log.d("Notificacion", "Notificación ${if (delay > 0) "programada" else "inmediata"} para '${tarea.titulo}'")
//            } else {
//                Log.d("Notificacion", "Tarea no requiere recordatorio o fecha vacía")
//            }
//        } catch (e: Exception) {
//            Log.e("Notificacion", "Error al programar notificación", e)
//        }
//    }
}
