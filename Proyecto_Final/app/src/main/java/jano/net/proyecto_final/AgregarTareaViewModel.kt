import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

class AgregarTareaViewModelC : ViewModel() {
    private val _title = mutableStateOf("")
    val title: State<String> get() = _title

    private val _description = mutableStateOf("")
    val description: State<String> get() = _description

    private val _content = mutableStateOf("")
    val content: State<String> get() = _content

    private val _posponer = mutableStateOf(false)
    val posponer: State<Boolean> get() = _posponer

    private val _startDate = mutableStateOf("")
    val startDate: State<String> get() = _startDate

    private val _endDate = mutableStateOf("")
    val endDate: State<String> get() = _endDate


    // Valores predeterminados
    private val defaultTitle = "Escribe tu título aquí"
    private val defaultDescription = "Escribe tu descripción aquí"
    private val defContent = "Escribe tu texto aquí"

    init {
        _title.value = defaultTitle
        _description.value = defaultDescription
        _content.value = defContent
    }

    fun onContentChange(newContent: String) {
        if (newContent.isNotEmpty() && newContent == defContent) {
            _content.value = ""
        } else {
            _content.value = newContent
        }
    }

    fun onTitleChange(newTitle: String) {
        if (newTitle.isNotEmpty() && newTitle == defaultTitle) {
            _title.value = ""
        } else {
            _title.value = newTitle
        }
    }

    fun onDescriptionChange(newDescription: String) {
        if (newDescription.isNotEmpty() && newDescription == defaultDescription) {
            _description.value = ""
        } else {
            _description.value = newDescription
        }
    }

    fun setStartDate(date: String) {
        _startDate.value = date
    }

    fun setEndDate(date: String) {
        _endDate.value = date
    }

    fun togglePosponer() {
        _posponer.value = !_posponer.value
    }
}
