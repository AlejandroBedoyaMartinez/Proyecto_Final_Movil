package com.example.inventory.ui.tarea

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.dataTarea.Tarea
import com.example.inventory.dataTarea.tareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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

    fun savedTarea(imagenes:List<String>)
    {
        val tarea= Tarea(
            titulo = titulo.value,
            descripcion = descripcion.value,
            cuerpo = descripcionCuerpo.value,
            texto = texto.value,
            fechaIncio = fechaInicio.value,
            fechaFin = fechaFin.value,
            recordar = recordar.value,
            hecho = false,
            imagenes = imagenes
        )
        viewModelScope.launch {
            tareaRepository.insertTarea(tarea)
        }
    }

    fun editTarea(imagenes:List<String>)
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
            imagenes = imagenes
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
}
