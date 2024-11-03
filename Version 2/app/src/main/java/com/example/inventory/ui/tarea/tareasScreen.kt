package com.example.inventory.ui.tarea

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import com.example.inventory.R
import com.example.inventory.viewModel


@Composable
fun tareasScreen(navHostController: NavHostController, viewModel: viewModel) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTablet = configuration.screenWidthDp > 600

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Margen alrededor de todo el fondo de la pantalla
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(Modifier.padding(top = 25.dp)) {
            BuscarText(query = viewModel.query.value, onQueryChanged = { viewModel.query.value = it })
        }

        if (isTablet) {
            // Layout específico para tablet: lista de tareas a la izquierda y botón a la derecha
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(45.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(13) {
                        val color = if (it % 2 == 0) Color.LightGray else Color.White
                        GenerarTarea(
                            tarea = "Tarea ${it + 1}",
                            colorFondo = color,
                            checked = false,
                            onCheckedChange = {}
                        )
                    }
                }

                // Botón de agregar a la derecha en tablet
                Button(
                    onClick = { navHostController.navigate("agregarNueva") },
                    modifier = Modifier
                        .padding(50.dp)
                        .align(Alignment.Bottom)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(text = stringResource(R.string.agregar_nueva), color = Color.Black)
                }
            }
        } else {
            // Layout para celular
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(19) {
                        val color = if (it % 2 == 0) Color.LightGray else Color.White
                        GenerarTarea(
                            tarea = "Tarea ${it + 1}",
                            colorFondo = color,
                            checked = false,
                            onCheckedChange = {}
                        )
                    }
                }

                // Botón de agregar en la esquina inferior derecha
                Button(
                    onClick = { navHostController.navigate("agregarNueva") },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(if (isLandscape) Alignment.BottomEnd else Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(text = stringResource(R.string.agregar_nueva), color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun GenerarTarea(
    tarea: String,
    colorFondo: Color,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(colorFondo)
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = tarea, Modifier.align(Alignment.CenterVertically))
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarText(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(text = stringResource(R.string.buscar)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Icono de búsqueda"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Gray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray
        ),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )
}
