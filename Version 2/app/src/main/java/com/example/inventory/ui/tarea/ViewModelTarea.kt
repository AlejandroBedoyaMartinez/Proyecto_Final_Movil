package com.example.inventory.ui.tarea

import android.icu.util.Calendar
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.inventory.dataTarea.Tarea
import com.example.inventory.dataTarea.tareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import com.example.inventory.notifications.NotificationWorker
import java.util.UUID

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
    var hora = mutableStateOf("")
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

    fun savedTarea(
        imagenes: List<String>,
        videos: List<String>) {
        val tarea = Tarea(
            titulo = titulo.value,
            descripcion = descripcion.value,
            cuerpo = descripcionCuerpo.value,
            texto = texto.value,
            fechaIncio = fechaInicio.value,
            fechaFin = fechaFin.value,
            recordar = recordar.value,
            hecho = false,
            imagenes = imagenes,
            videos = videos
        )

        viewModelScope.launch {
            tareaRepository.insertTarea(tarea)

            // Programar notificación a la hora específica
            if (tarea.recordar && tarea.fechaFin.isNotEmpty()) {
                val dueDateMillis = convertirFechaYHoraAMillis(tarea.fechaFin, hora.value)
                if (dueDateMillis != null && dueDateMillis > System.currentTimeMillis()) {
                    val delay = dueDateMillis - System.currentTimeMillis()
                  val workerid =  programarWorker(
                        delay = delay,
                        titulo = "Recordatorio de tarea",
                        mensaje = "No olvides completar la tarea '${tarea.titulo}' a la hora programada."
                    )
                }
            }

            val dueDateMillis = convertirFechaAMillis(tarea.fechaFin)
            val hoy = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(System.currentTimeMillis())
            val fechaTarea = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dueDateMillis ?: 0)

            if (tarea.recordar && hoy == fechaTarea) {
                programarWorker(
                    delay = 0,
                    titulo = "Tarea para hoy",
                    mensaje = "La tarea '${tarea.titulo}' debe completarse hoy."
                )
            }
        }
    }

    fun cancelarNotificacion(workerId: String) {
        try {
            val uuid = UUID.fromString(workerId)
            WorkManager.getInstance().cancelWorkById(uuid)
            Log.d("Notificacion", "Notificación con ID $workerId cancelada correctamente")
        } catch (e: Exception) {
            Log.e("Notificacion", "Error al cancelar notificación con ID $workerId", e)
        }
    }
    
    fun convertirFechaYHoraAMillis(fecha: String, hora: String): Long? {
        return try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dateTime = "$fecha $hora"
            val date = dateFormat.parse(dateTime)
            date?.time
        } catch (e: Exception) {
            Log.e("Notificacion", "Error al convertir fecha y hora: $fecha $hora", e)
            null
        }
    }

    fun programarWorker(delay: Long, titulo: String, mensaje: String): UUID {
        val data = androidx.work.Data.Builder()
            .putString("titulo", titulo)
            .putString("mensaje", mensaje)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        val workManager = WorkManager.getInstance()
        workManager.enqueue(workRequest)

        return workRequest.id
    }


    fun editTarea(imagenes:List<String>,videos:List<String>)
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
            hecho = hecho.value,
            imagenes = imagenes,
            videos = videos
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
                tareasPendientes.forEach { tarea ->
                    // Construir mensaje para cada tarea
                    val mensaje = "La tarea : ${tarea.titulo}, queda pendiente (Recordatorio de las 5pm (: )"

                    // Crear los datos para la notificación
                    val data = androidx.work.Data.Builder()
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
}
