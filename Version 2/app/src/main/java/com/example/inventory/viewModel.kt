package com.example.inventory

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.inventorydata.notaEntity
import com.example.inventorydata.notaRepository
import kotlinx.coroutines.launch

class viewModel(
    private val notaRepository: notaRepository
) :ViewModel() {
    var query = mutableStateOf("")
    var titulo = mutableStateOf("")
    var descripcion = mutableStateOf("")
    var descripcionCuerpo = mutableStateOf("")
    var texto = mutableStateOf("")
    var banderaSwitch = mutableStateOf(false)
    var notas: MutableList<Nota> = mutableListOf()
//var state by mutableStateOf(notasScreen(navHostController = rememberNavController(), viewModel = viewModel ))
  //  private set

    fun tituloVacio(): Boolean {
    return if(titulo.value == "")false else true
    }


    init{
        viewModelScope.launch {
           notas = notaRepository.getNotas().toMutableList()
        }
    }

    fun savedNota()
    {
        val nota= Nota(
            titulo = titulo.value,
            descripcion = descripcion.value,
            cuerpo = descripcionCuerpo.value,
            texto = texto.value,
            )
        viewModelScope.launch {
            notaRepository.insertNota(nota)
        }
    }
}
