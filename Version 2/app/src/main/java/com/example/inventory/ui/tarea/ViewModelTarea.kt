package com.example.inventory.ui.tarea

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.inventory.MainActivity
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

            programarNotificacion(nuevaTarea) // al momenteo


            // Notificación inmediata si vence hoy
            val dueDateMillis = convertirFechaAMillis(nuevaTarea.fechaFin)
            val hoy = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(System.currentTimeMillis())
            val fechaTarea = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dueDateMillis ?: 0)

            // Si la tarea tiene la opción de recordar activada y vence hoy, enviar notificación inmediata
            if (nuevaTarea.recordar && hoy == fechaTarea) {
                programarWorker(
                    delay = 0, // Notificación inmediata
                    titulo = "Tarea para hoy",
                    mensaje = "La tarea '${nuevaTarea.titulo}' debe completarse hoy."
                )
            }

            // También programar una notificación para el vencimiento de la tarea, si aplica
            if (nuevaTarea.recordar && nuevaTarea.fechaFin.isNotEmpty() && !nuevaTarea.hecho) {
                val dueDateMillis = convertirFechaAMillis(nuevaTarea.fechaFin) ?: return@launch
                val now = System.currentTimeMillis()

                // Si la tarea vence dentro de 4 horas, programar una notificación
                val cuatroHorasAntes = dueDateMillis - TimeUnit.HOURS.toMillis(1)
                if (cuatroHorasAntes > now) {
                    val delayCuatroHoras = cuatroHorasAntes - now
                    Log.d("Notificacion", "Programando notificación para tarea '${nuevaTarea.titulo}' 4 horas antes del vencimiento")
                    programarWorker(
                        delay = delayCuatroHoras,
                        titulo = "Tarea pendiente",
                        mensaje = "La tarea '${nuevaTarea.titulo}' se vence mañana."
                    )
                }
            }
        }
    }





    // funcion de Notificación inmediata si vence hoy
    fun programarWorker(delay: Long, titulo: String, mensaje: String) {
        Log.d("Notificacion", "Programando notificación: Título='$titulo', Mensaje='$mensaje', Delay=${delay}ms")
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

    // Notificación 4 horas antes dl dia siguente que es cuando vence
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
            set(Calendar.HOUR_OF_DAY, 17) // Programar a las 5 pm
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis < ahora) {
            calendar.add(Calendar.DAY_OF_YEAR, 1) // Si ya pasó hoy, programar para mañana
        }

        val delay = calendar.timeInMillis - ahora

        viewModelScope.launch {
            // Obtener tareas pendientes desde el repositorio
            val tareasPendientes = _tareas.value.filter { tarea ->
                !tarea.hecho && convertirFechaAMillis(tarea.fechaFin)?.let { it >= ahora } == true
            }

            if (tareasPendientes.isNotEmpty()) {
                // Programar una notificación separada por cada tarea pendiente
                tareasPendientes.forEach { tarea ->
                    // Construir mensaje para cada tarea
                    val mensaje = "Tienes una tarea pendiente: ${tarea.titulo}"

                    // Crear un identificador único para cada tarea (puede ser el ID de la tarea)
                    val notificationId = tarea.id

                    // Crear los datos para la notificación
                    val data = Data.Builder()
                        .putString("titulo", "Tarea pendiente: ${tarea.titulo}")
                        .putString("mensaje", mensaje)
                        .build()

                    // Configurar el retraso para la notificación
                    val workerDelay = calcularRetrasoParaNotificacion(tarea, ahora)

                    // Programar la notificación con WorkManager
                    val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                        .setInitialDelay(workerDelay, TimeUnit.MILLISECONDS)
                        .setInputData(data)
                        .build()

                    // Enviar la tarea al WorkManager
                    WorkManager.getInstance().enqueue(notificationWorkRequest)

                    Log.d("Notificacion", "Programando notificación para tarea '${tarea.titulo}' con retraso de $workerDelay ms")
                }
            }
        }
    }

    // Función auxiliar para calcular el retraso de la notificación según la tarea
    fun calcularRetrasoParaNotificacion(tarea: Tarea, ahora: Long): Long {
        // Calcular la fecha de vencimiento de la tarea en milisegundos
        val dueDateMillis = convertirFechaAMillis(tarea.fechaFin) ?: return 0

        // Asegurarse de que la tarea se programe para el momento correcto
        val retraso = dueDateMillis - ahora

        return if (retraso > 0) {
            retraso
        } else {
            // Si la fecha ya pasó, no programar la notificación (o programar para el siguiente día, según tu lógica)
            0
        }
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

    //1. pedir permiso para mostrar notificaciones, a la hoora de querer guardar una tarea y esta tenga habilitado el check de recordarme.
}




