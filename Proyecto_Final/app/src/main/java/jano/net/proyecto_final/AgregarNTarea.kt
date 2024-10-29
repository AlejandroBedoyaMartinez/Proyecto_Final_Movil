package jano.net.proyecto_final

import AgregarTareaViewModelC
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import java.util.*
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarNuevaT(viewModel: AgregarTareaViewModelC = viewModel()) {
    //var posponer by remember { mutableStateOf(false) }
    //var startDate by remember { mutableStateOf("") }
    //var endDate by remember { mutableStateOf("") }

    val posponer by viewModel.posponer
    val startDate by viewModel.startDate
    val endDate by viewModel.endDate
    val title by viewModel.title
    val description by viewModel.description
    val content by viewModel.content

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Function to show DatePickerDialog
    fun showDatePicker(onDateSelected: (String) -> Unit) {
        DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                onDateSelected("$selectedDay/${selectedMonth + 1}/$selectedYear")
            },
            year, month, day
        ).show()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        // Title Field
        TextField(

            value = title,
            onValueChange = { viewModel.onTitleChange(it) },
            modifier = Modifier
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                //focusedTextColor = MaterialTheme.colorScheme.onBackground,
                //unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )

        // Start Date Picker
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "$startDate",  color = MaterialTheme.colorScheme.onBackground)
            Button(onClick = { showDatePicker { date -> viewModel.setStartDate(date) } }) {
                Text(text = stringResource(R.string.seleccionar_fecha_de_inicio))
            }
        }

        // End Date Picker
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "$endDate",  color = MaterialTheme.colorScheme.onBackground)
            Button(onClick = { showDatePicker { date -> viewModel.setEndDate(date) } }) {
                Text(text = stringResource(R.string.seleccionar_fecha_de_fin))
            }
        }

        // Description Field
        TextField(
            value = description,
            onValueChange = { viewModel.onDescriptionChange(it) },
            modifier = Modifier
                .height(250.dp)
                .padding(top = 10.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary
                //focusedTextColor = MaterialTheme.colorScheme.onBackground,
                //unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )

        TextField(
            value = content,
            onValueChange = { viewModel.onContentChange(it) },
            modifier = Modifier
                .height(250.dp)
                .padding(top = 10.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                //focusedTextColor = MaterialTheme.colorScheme.onBackground,
                //unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
        Row (
            modifier = Modifier
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.tomar_foto),
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.adjuntar_foto),
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }


            if (posponer) {
                PosponerTarea(onDismiss = { viewModel.togglePosponer() })
            }
            Button(
                onClick = { viewModel.togglePosponer() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.guardar),
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }
        }
    }
}





