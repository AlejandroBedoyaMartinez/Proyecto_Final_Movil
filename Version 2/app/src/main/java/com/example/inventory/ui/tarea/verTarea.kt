package com.example.inventory.ui.tarea

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inventory.R
import com.example.inventory.ui.nota.FullScreenImageScreen
import com.example.inventory.verVideoPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun verTarea(navController: NavController, viewModelTarea: ViewModelTarea, id:Int){
    BackHandler {
        navController.popBackStack()
        viewModelTarea.limpiarVariables()
    }
    LaunchedEffect(id) {
        viewModelTarea.getTarea(id)
    }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val scrollState = rememberScrollState()

    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }

    fechaInicio = viewModelTarea.fechaInicio.value.toString()
    fechaFin = viewModelTarea.fechaFin.value.toString()

    val datePickerDialogInicio = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            fechaInicio = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    val datePickerDialogFin = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            fechaFin = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    var fullScreenImageUri by remember { mutableStateOf<Uri?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
                .padding(WindowInsets.navigationBars.asPaddingValues()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                color = MaterialTheme.colorScheme.surface,
                text = stringResource(R.string.ver),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = viewModelTarea.titulo.value, onValueChange = {
                    viewModelTarea.titulo.value = it
                },
                enabled = false,
                placeholder = { Text(text = stringResource(R.string.escribe_un_titulo)) },
                modifier = Modifier
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(10.dp)
            )
            Row(
                modifier = Modifier.width(280.dp)
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Icono calendario",
                            tint = MaterialTheme.colorScheme.surface
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (fechaInicio.isEmpty()) stringResource(R.string.elegir_fecha) else fechaInicio,
                            color = MaterialTheme.colorScheme.surface,
                            fontSize = 13.5.sp
                        )
                    }
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Icono calendario",
                            tint = MaterialTheme.colorScheme.surface
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text = if (fechaFin.isEmpty()) stringResource(R.string.elegir_fecha) else fechaFin,
                            color = MaterialTheme.colorScheme.surface,
                            fontSize = 13.5.sp
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Botón para agregar una nueva hora
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    if (viewModelTarea.hora.isNotEmpty()) {
                        Text(text = "Horas programadas:")
                        viewModelTarea.hora.forEachIndexed { index, hora ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = hora,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    } else {
                        Text(text = "No hay horas programadas aún.")
                    }
                }
            }
            Spacer(modifier = Modifier.size(5.dp))
            TextField(
                value = viewModelTarea.descripcion.value,
                onValueChange = { viewModelTarea.descripcion.value = it },
                enabled = false,
                placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
                modifier = Modifier
                    .height(100.dp)
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                /*colors = textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )*/
            )
            TextField(
                value = viewModelTarea.descripcionCuerpo.value,
                onValueChange = { viewModelTarea.descripcionCuerpo.value = it },
                enabled = false,
                placeholder = { Text(text = stringResource(R.string.cuerpo)) },
                modifier = Modifier
                    .height(220.dp)
                    .padding(top = 10.dp)
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                /* colors = textFieldColors(
                     containerColor = MaterialTheme.colorScheme.primary,
                     focusedTextColor = MaterialTheme.colorScheme.surface,
                     cursorColor = MaterialTheme.colorScheme.surface,
                     unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                 )*/
            )
            TextField(
                value = viewModelTarea.texto.value,
                onValueChange = { viewModelTarea.texto.value = it },
                enabled = false,
                placeholder = { Text(text = stringResource(R.string.ingrese_el_texto)) },
                modifier = Modifier
                    .height(220.dp)
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                /* colors = textFieldColors(
                     containerColor = MaterialTheme.colorScheme.primary,
                     focusedTextColor = MaterialTheme.colorScheme.surface,
                     cursorColor = MaterialTheme.colorScheme.surface,
                     unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                 )*/
            )

            val videoUriList = remember { mutableStateListOf<Uri?>() }
            LaunchedEffect(viewModelTarea.tarea.videos) {
                videoUriList.clear()
                videoUriList.addAll(viewModelTarea.tarea.videos.map { Uri.parse(it) })
            }
            for (uri in videoUriList) {
                if (uri != null && !uri.toString().equals("") ){
                    Box(
                        Modifier
                            .width(300.dp)
                            .height(300.dp)
                    ) {
                        verVideoPlayer(
                            videoUri = uri!!
                        )
                    }
                }
            }

            val imageUriList = remember { mutableStateListOf<Uri?>() }

            LaunchedEffect(viewModelTarea.tarea.imagenes) {
                imageUriList.clear()
                imageUriList.addAll(viewModelTarea.tarea.imagenes.map { Uri.parse(it) })
            }

            for (uri in imageUriList) {
                if (uri != null && !uri.toString().equals("") ){
                    Box(
                        Modifier
                            .width(230.dp)
                            .clickable { fullScreenImageUri = uri }
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Selected image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
        if (fullScreenImageUri != null) {
            FullScreenImageScreen(
                imageUri = fullScreenImageUri!!,
                onBack = { fullScreenImageUri = null }
            )
        }
    }
}
