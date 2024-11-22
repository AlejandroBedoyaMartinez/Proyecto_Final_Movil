package jano.net.proyecto_final

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
<<<<<<< HEAD
@Composable
fun AgregarNueva(viewModel: viewModel){
    var posponer by remember { mutableStateOf(true) }
=======
@Composable //aqui es para notas, agregar una nueva nota
fun AgregarNueva(
){
    var posponer by remember { mutableStateOf(false) }
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
    Column (
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(50.dp))
<<<<<<< HEAD
        TextField(
            value = viewModel.titulo.value, onValueChange = { viewModel.titulo.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_el_t_tulo)) },
=======
        TextField(value = stringResource(R.string.titulo), onValueChange = {},
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
            modifier = Modifier
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
<<<<<<< HEAD
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )
        var texto:String = stringResource(R.string.convertir_a_nota);
        if(viewModel.banderaSwitch.value){
            texto = stringResource(R.string.convertir_a_tarea);
        }
=======
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
        Row (
            Modifier
                .align(Alignment.End)
                .padding(end = 50.dp)){
<<<<<<< HEAD
            Text(
                text = texto,
                Modifier.align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.surface
            )
            Switch(
                checked = viewModel.banderaSwitch.value,
                onCheckedChange = {viewModel.banderaSwitch.value = it},
=======
            Text( text = stringResource(R.string.convertir_a) ,Modifier.align(Alignment.CenterVertically))
            Switch(

                checked = true,
                onCheckedChange = {},
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color.Gray,
                    uncheckedThumbColor = Color.LightGray,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }
<<<<<<< HEAD
        TextField(
            value = viewModel.descripcion.value, onValueChange = { viewModel.descripcion.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
            modifier = Modifier
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
            value = viewModel.descripcionCuerpo.value, onValueChange = { viewModel.descripcionCuerpo.value = it},
            placeholder = { Text(text = stringResource(R.string.cuerpo)) },
=======

        TextField( value = stringResource(R.string.descripcion), onValueChange = {},
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
            modifier = Modifier
                .height(250.dp)
                .padding(top = 10.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
<<<<<<< HEAD
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )
        TextField(
            value = viewModel.texto.value, onValueChange = { viewModel.texto.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_el_texto)) },
=======
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
        TextField(value = stringResource(R.string.tu_texto_aqui), onValueChange = {},
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
            modifier = Modifier
                .height(250.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
<<<<<<< HEAD
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
=======
                containerColor = MaterialTheme.colorScheme.primary
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
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

<<<<<<< HEAD
            if(posponer and viewModel.banderaSwitch.value) {
                PosponerTarea( onDismiss = { posponer = false })
            }

            Button(
                onClick = {
                    if(viewModel.banderaSwitch.value == true) {
                        posponer = true
                    }
                   },
=======

            if(posponer) {
                PosponerTarea(onDismiss = { posponer = false })
            }
            Button(
                onClick = { posponer = true},
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
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


