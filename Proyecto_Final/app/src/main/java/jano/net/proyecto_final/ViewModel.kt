package jano.net.proyecto_final

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class AgregarTareaViewModel : ViewModel() {
    private val _startDate = mutableStateOf("")
    val startDate: State<String> = _startDate

    private val _endDate = mutableStateOf("")
    val endDate: State<String> = _endDate

    fun setStartDate(date: String) {
        _startDate.value = date
    }

    fun setEndDate(date: String) {
        _endDate.value = date
    }
}
