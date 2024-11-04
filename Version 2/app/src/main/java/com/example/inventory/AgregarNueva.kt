package com.example.inventory

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inventory.dataNota.Nota
import com.example.inventory.dataTarea.Tarea
import com.example.inventory.ui.nota.viewModelNota
import com.example.inventory.ui.tarea.PosponerTarea
import com.example.inventory.ui.tarea.ViewModelTarea


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarNueva(navController: NavController, viewModelNota: viewModelNota,viewModelTarea: ViewModelTarea){

    BackHandler {
            navController.popBackStack()
    }
    var posponer by remember { mutableStateOf(true) }
    val context = LocalContext.current


    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 600
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()
    val paddingValue = if (isTablet) 70.dp else 10.dp

    // Define el padding general para toda la columna
    val columnPadding = if (isTablet && !isLandscape) 16.dp else 0.dp




    // Define el ancho de los TextFields
    val textFieldWidth = when {
        isTablet -> 500.dp
        isLandscape -> 400.dp
        else -> 300.dp
    }


    Column (
        Modifier
            .fillMaxSize()
            .padding(columnPadding)
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier

            .height(20.dp))
        Text(
            color = MaterialTheme.colorScheme.surface,
            text = stringResource(R.string.agregar_nueva),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = viewModelNota.titulo.value,
            onValueChange = { viewModelNota.titulo.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_el_t_tulo)) },
            modifier = Modifier
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )


        var texto:String = stringResource(R.string.convertir_a_tarea);
        if(viewModelNota.banderaSwitch.value){
            texto = stringResource(R.string.convertir_a_nota );
        }
        Row (
            Modifier
                .align(Alignment.End)
                .padding(end = paddingValue)){
            Text(
                text = texto,
                Modifier
                    .align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.surface
            )
            Switch(
                checked = viewModelNota.banderaSwitch.value,
                onCheckedChange = {viewModelNota.banderaSwitch.value = it},
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color.Gray,
                    uncheckedThumbColor = Color.LightGray,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }
        TextField(
            value = viewModelNota.descripcion.value, onValueChange = { viewModelNota.descripcion.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
            modifier = Modifier
                .width(textFieldWidth)
                .height(100.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )
        TextField(
            value = viewModelNota.descripcionCuerpo.value, onValueChange = { viewModelNota.descripcionCuerpo.value = it},
            placeholder = { Text(text = stringResource(R.string.cuerpo)) },
            modifier = Modifier
                .width(textFieldWidth)
                .height(230.dp)
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
        TextField(
            value = viewModelNota.texto.value, onValueChange = { viewModelNota.texto.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_el_texto)) },
            modifier = Modifier
                .height(230.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )
        Row (
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(bottom = if (isTablet) 40.dp else 10.dp),
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

            if(posponer and viewModelNota.banderaSwitch.value) {
                var tarea = Tarea(
                    id = 0,
                    titulo = viewModelNota.titulo.value,
                    descripcion = viewModelNota.descripcion.value,
                    cuerpo = viewModelNota.descripcionCuerpo.value,
                    texto = viewModelNota.texto.value,
                    fechaIncio = "",
                    fechaFin = "",
                    recordar = false,
                    hecho = false
                )
                PosponerTarea(
                    onDismiss = {
                        posponer = false
                    },
                    viewModelTarea = viewModelTarea,
                    tarea = tarea,
                    onTareaGuardada = {
                        viewModelNota.limpiarVariables()
                        navController.navigate("tareas") {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            Button(
                onClick = {
                    if(viewModelNota.banderaSwitch.value == true) {
                        posponer = true
                    }else if(viewModelNota.tituloVacio()){
                        viewModelNota.savedNota()
                        viewModelNota.limpiarVariables()
                        navController.navigate("notas") {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }else{
                        Toast.makeText(context,
                            context.getString(R.string.escribe_un_titulo), Toast.LENGTH_SHORT).show()
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


