package com.example.inventory.ui.nota

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

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
import com.example.inventory.R.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun verNota(navController: NavController, viewModelNota: viewModelNota, id:Int, windowSizeClass: WindowWidthSizeClass){

    BackHandler {
        navController.popBackStack()
        viewModelNota.limpiarVariables()
    }

    LaunchedEffect(id) {
        viewModelNota.getNota(id)
    }

    val textfieldWidth = when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> 1f  // Teléfonos en orientación vertical
        WindowWidthSizeClass.Medium -> 0.8f // Tablets pequeñas o teléfonos en horizontal
        WindowWidthSizeClass.Expanded -> 0.6f // Tablets grandes o escritorios
        else -> 1f
    }

    val scrollState = rememberScrollState() //para el escroleado
    val textFieldHeightSmall = 100.dp
    val textFieldPadding = 3.dp

    Column (
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            color = MaterialTheme.colorScheme.surface,
            text = stringResource(R.string.ver),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = viewModelNota.titulo.value,
            onValueChange =
            {
                viewModelNota.titulo.value = it
            },
            enabled = false,
            placeholder = { Text(text = stringResource(string.ingrese_el_t_tulo)) },
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(textfieldWidth)
                .height(textFieldHeightSmall)
                .padding(textFieldPadding)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )

        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(10.dp))
        TextField(
            value = viewModelNota.descripcion.value, onValueChange = { viewModelNota.descripcion.value = it},
            enabled = false,
            placeholder = { Text(text = stringResource(string.ingrese_la_descripcion)) },
            modifier = Modifier
                .fillMaxWidth(textfieldWidth)
                .height(textFieldHeightSmall)
                .padding(textFieldPadding)
                .height(100.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )

        )
        TextField(
            value = viewModelNota.descripcionCuerpo.value, onValueChange = { viewModelNota.descripcionCuerpo.value = it},
            enabled = false,
            placeholder = { Text(text = stringResource(string.cuerpo)) },
            modifier = Modifier
                .fillMaxWidth(textfieldWidth)
                .height(textFieldHeightSmall)
                .padding(textFieldPadding)
                .height(250.dp)
                .padding(top = 10.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )

        )
        TextField(
            value = viewModelNota.texto.value, onValueChange = { viewModelNota.texto.value = it},
            enabled = false,
            placeholder = { Text(text = stringResource(string.ingrese_el_texto)) },
            modifier = Modifier
                .fillMaxWidth(textfieldWidth)
                .height(textFieldHeightSmall)
                .padding(textFieldPadding)
                .height(250.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                 )

            )
         }
    }
