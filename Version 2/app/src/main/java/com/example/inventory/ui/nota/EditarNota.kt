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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inventory.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editarNota(navController: NavController, viewModelNota: viewModelNota, id:Int){

    LaunchedEffect(id) {
        viewModelNota.getNota(id)
    }

    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val islandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTablet = configuration.screenWidthDp > 600


    val textfieldWidth = when {
        isTablet && islandscape -> 0.50f
        isTablet -> 0.6f
        islandscape ->0.8f
        else -> 1f
    }

    val textFieldHeightSmall = 100.dp
    val textFieldHeightLarge = 350.dp
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
            text = stringResource(R.string.editar),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = viewModelNota.titulo.value, onValueChange = {
                viewModelNota.titulo.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_el_t_tulo)) },
            modifier = Modifier
                .fillMaxWidth(textfieldWidth)
                .height(textFieldHeightSmall)
                .padding(textFieldPadding)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(10.dp))




        if(islandscape) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {

                Column(
                    Modifier
                        .weight(0.4f)
                        .padding(end = 16.dp)
                ) {
                    TextField(
                        value = viewModelNota.descripcion.value,
                        onValueChange = { viewModelNota.descripcion.value = it },
                        placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
                        modifier = Modifier
                            .fillMaxWidth(textfieldWidth)
                            .height(textFieldHeightSmall)
                            .padding(textFieldPadding)
                            .background(Color.White)
                            .border(BorderStroke(1.dp, Color.Black)),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.surface,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Column(
                    Modifier
                        .weight(0.4f)
                        .padding(end = 16.dp)
                ) {
                    TextField(
                        value = viewModelNota.descripcionCuerpo.value,
                        onValueChange = { viewModelNota.descripcionCuerpo.value = it },
                        placeholder = { Text(text = stringResource(R.string.cuerpo)) },
                        modifier = Modifier
                            .fillMaxWidth(textfieldWidth)
                            .height(textFieldHeightSmall)
                            .padding(textFieldPadding)
                            .height(250.dp)
                            .padding(top = 10.dp)
                            .background(Color.White)
                            .border(BorderStroke(1.dp, Color.Black)),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.surface,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                        )
                    )


                }
            }
        }else {
            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                value = viewModelNota.descripcion.value,
                onValueChange = { viewModelNota.descripcion.value = it },
                placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
                modifier = Modifier
                    .fillMaxWidth(textfieldWidth)
                    .height(textFieldHeightSmall)
                    .padding(textFieldPadding)
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )

            )

            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                value = viewModelNota.texto.value, onValueChange = { viewModelNota.texto.value = it},
                placeholder = { Text(text = stringResource(R.string.ingrese_el_texto)) },
                modifier = Modifier
                    .height(250.dp)
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color.Black)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.size(10.dp))

            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(bottom = if (isTablet) 60.dp else 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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

                Button(
                    onClick = {
                        if (viewModelNota.tituloVacio()) {
                            viewModelNota.editNota()
                            viewModelNota.limpiarVariables()
                            navController.navigate("notas")
                        }
                    },
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
}