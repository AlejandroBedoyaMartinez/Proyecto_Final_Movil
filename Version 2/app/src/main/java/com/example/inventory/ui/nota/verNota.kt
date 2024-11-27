package com.example.inventory.ui.nota

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
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
import com.example.inventory.R.*
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun verNota(navController: NavController, viewModelNota: viewModelNota, id: Int) {
    val scrollState = rememberScrollState()

    BackHandler {
        navController.popBackStack()
        viewModelNota.limpiarVariables()
    }

    LaunchedEffect(id) {
        viewModelNota.getNota(id)
    }

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
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = viewModelNota.titulo.value,
                onValueChange = { viewModelNota.titulo.value = it },
                enabled = false,
                placeholder = { Text(text = stringResource(string.ingrese_el_t_tulo)) },
                modifier = Modifier
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.fillMaxWidth().size(10.dp))
            TextField(
                value = viewModelNota.descripcion.value,
                onValueChange = { viewModelNota.descripcion.value = it },
                enabled = false,
                placeholder = { Text(text = stringResource(string.ingrese_la_descripcion)) },
                modifier = Modifier
                    .height(100.dp)
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )
            )
            TextField(
                value = viewModelNota.descripcionCuerpo.value,
                onValueChange = { viewModelNota.descripcionCuerpo.value = it },
                enabled = false,
                placeholder = { Text(text = stringResource(string.cuerpo)) },
                modifier = Modifier
                    .height(250.dp)
                    .padding(top = 10.dp)
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )
            )
            TextField(
                value = viewModelNota.texto.value,
                onValueChange = { viewModelNota.texto.value = it },
                enabled = false,
                placeholder = { Text(text = stringResource(string.ingrese_el_texto)) },
                modifier = Modifier
                    .height(250.dp)
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )
            )
            val imageUriList = remember { mutableStateListOf<Uri?>() }

            LaunchedEffect(viewModelNota.nota.imagenes) {
                imageUriList.clear()
                imageUriList.addAll(viewModelNota.nota.imagenes.map { Uri.parse(it) })
            }

            for (uri in imageUriList) {
                if (uri != null) {
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
