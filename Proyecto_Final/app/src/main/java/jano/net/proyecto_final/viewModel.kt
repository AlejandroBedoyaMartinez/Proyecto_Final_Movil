package jano.net.proyecto_final

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class viewModel:ViewModel() {
    var query = mutableStateOf("")
    var titulo = mutableStateOf("")
    var descripcion = mutableStateOf("")
    var descripcionCuerpo = mutableStateOf("")
    var texto = mutableStateOf("")
    var banderaSwitch = mutableStateOf(false)
}
