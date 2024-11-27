package com.example.inventory.ui.nota

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.dataNota.notaRepository
import com.example.inventory.dataNota.Nota
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class viewModelNota(
    private val notaRepository: notaRepository
) :ViewModel() {
    var query = mutableStateOf("")
    var titulo = mutableStateOf("")
    var descripcion = mutableStateOf("")
    var descripcionCuerpo = mutableStateOf("")
    var texto = mutableStateOf("")
    var banderaSwitch = mutableStateOf(false)
    private val _notas: MutableStateFlow<List<Nota>> = MutableStateFlow(emptyList())
    val notas: StateFlow<List<Nota>> = _notas
    var nota: Nota = Nota(0,"","","","")

    init {
        viewModelScope.launch {
            notaRepository.getNotas().collect { notasList ->
                _notas.value = notasList
            }
        }
    }

    fun getNota(id: Int) {
        viewModelScope.launch {
            val notaActual = notaRepository.getNota(id).first()
            notaActual?.let {
                titulo.value = it.titulo
                descripcion.value = it.descripcion
                descripcionCuerpo.value = it.cuerpo
                texto.value = it.texto
                nota  = it
            }
        }
    }

    fun savedNota(imagenes:List<String>)
    {
        val nota= Nota(
            titulo = titulo.value,
            descripcion = descripcion.value,
            cuerpo = descripcionCuerpo.value,
            texto = texto.value,
            imagenes = imagenes
            )
        viewModelScope.launch {
            notaRepository.insertNota(nota)
        }
    }

    fun editNota(imagenes: List<String>)
    {
        val notaActualizar= Nota(
            id = nota.id,
            titulo = titulo.value,
            descripcion = descripcion.value,
            cuerpo = descripcionCuerpo.value,
            texto = texto.value,
            imagenes = imagenes
        )
        viewModelScope.launch {
            notaRepository.editNota(notaActualizar)
        }
    }

    fun deleteNota(notaEntity: Nota)
    {
        viewModelScope.launch {
            notaRepository.deleteNota(notaEntity)
        }
    }

    fun tituloVacio(): Boolean {
        return if(titulo.value == "")false else true
    }


    fun limpiarVariables() {
        titulo.value = ""
        descripcion.value = ""
        descripcionCuerpo.value = ""
        texto.value = ""
        banderaSwitch.value = false
    }
}
