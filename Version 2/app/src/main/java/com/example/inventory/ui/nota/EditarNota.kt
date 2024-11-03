package com.example.inventory.ui.nota

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inventory.R
import com.example.inventory.viewModel

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editarNota(navController: NavController, viewModel: viewModel, id: Int) {
    LaunchedEffect(id) {
        viewModel.getNota(id)
    }

    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTablet = configuration.screenWidthDp > 600

    // Define el tamaño de los TextField basado en el tipo de dispositivo y la orientación
    val textFieldWidth = if (isTablet) 600.dp else if (isLandscape) 400.dp else 300.dp
    val textFieldHeightSmall = 100.dp
    val textFieldHeightLarge = 250.dp
    val textFieldPadding = 16.dp // Aumentar el padding

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            color = MaterialTheme.colorScheme.surface,
            text = stringResource(R.string.editar),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))

        // Campo del título
        TextField(
            value = viewModel.titulo.value,
            onValueChange = { viewModel.titulo.value = it },
            placeholder = { Text(text = stringResource(R.string.ingrese_el_t_tulo)) },
            modifier = Modifier
                .width(textFieldWidth)
                .height(textFieldHeightSmall) // Altura constante para el título
                .padding(textFieldPadding) // Aumentar el padding
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )

        Spacer(modifier = Modifier.size(10.dp))

        if (isLandscape) {
            // En orientación horizontal: descripción y contenido en la misma fila
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Columna de la descripción a la izquierda
                Column(
                    modifier = Modifier
                        .weight(1f)
                        //.padding(end = 8.dp)
                ) {
                    TextField(
                        value = viewModel.descripcion.value,
                        onValueChange = { viewModel.descripcion.value = it },
                        placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
                        modifier = Modifier
                            .width(textFieldWidth)
                            .height(textFieldHeightSmall) // Altura constante para descripción
                            .padding(textFieldPadding) // Aumentar el padding
                            .background(Color.White)
                            .border(BorderStroke(1.dp, Color.Black)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.surface,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                // Columna del contenido a la derecha
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    TextField(
                        value = viewModel.descripcionCuerpo.value,
                        onValueChange = { viewModel.descripcionCuerpo.value = it },
                        placeholder = { Text(text = stringResource(R.string.cuerpo)) },
                        modifier = Modifier
                            .width(textFieldWidth)
                            .height(textFieldHeightLarge) // Altura para el cuerpo
                            .padding(textFieldPadding) // Aumentar el padding
                            .background(Color.White)
                            .border(BorderStroke(1.dp, Color.Black)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.surface,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        } else {
            // En orientación vertical: descripción y contenido en columnas separadas
            TextField(
                value = viewModel.descripcion.value,
                onValueChange = { viewModel.descripcion.value = it },
                placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
                modifier = Modifier
                    .width(textFieldWidth)
                    .height(textFieldHeightSmall) // Altura constante para descripción
                    .padding(textFieldPadding) // Aumentar el padding
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                value = viewModel.descripcionCuerpo.value,
                onValueChange = { viewModel.descripcionCuerpo.value = it },
                placeholder = { Text(text = stringResource(R.string.cuerpo)) },
                modifier = Modifier
                    .width(textFieldWidth)
                    .height(textFieldHeightLarge) // Altura para el cuerpo
                    .padding(textFieldPadding) // Aumentar el padding
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        // Campo adicional de texto
        TextField(
            value = viewModel.texto.value,
            onValueChange = { viewModel.texto.value = it },
            placeholder = { Text(text = stringResource(R.string.ingrese_el_texto)) },
            modifier = Modifier
                .width(textFieldWidth)
                .height(textFieldHeightLarge) // Altura para el texto adicional
                .padding(textFieldPadding) // Aumentar el padding
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )

        // Botones
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
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
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.adjuntar_foto),
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Button(
                onClick = {
                    if (viewModel.tituloVacio()) {
                        viewModel.editNota()
                        viewModel.limpiarVariables()
                        navController.navigate("notas")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
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
