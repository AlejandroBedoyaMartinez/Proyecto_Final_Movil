package jano.net.proyecto_final

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
<<<<<<< HEAD
import androidx.compose.foundation.layout.width
=======
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
<<<<<<< HEAD
import androidx.compose.ui.text.TextStyle
=======
import androidx.compose.ui.tooling.preview.Preview
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
import androidx.navigation.NavHostController


@Composable
<<<<<<< HEAD
fun tareasScreen(navHostController: NavHostController,viewModel: viewModel) {
=======
fun tareasScreen(navHostController: NavHostController) {
    var query by remember { mutableStateOf("") }
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row (Modifier.padding(top = 25.dp)) {
<<<<<<< HEAD
            BuscarText(query = viewModel.query.value, onQueryChanged = { viewModel.query.value = it })
=======
            BuscarText(query = query, onQueryChanged = { query = it })
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
        }

        Box(
            modifier = Modifier
<<<<<<< HEAD
                .height(680.dp)
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.CenterHorizontally)
                .border(BorderStroke(2.dp, Color.Black))
                .width(330.dp)
=======
                .height(650.dp)
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.CenterHorizontally)
                .border(BorderStroke(2.dp, Color.Black))
                .fillMaxWidth()
                .padding(35.dp, 40.dp)
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
        ) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
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


            }
            Button(
<<<<<<< HEAD
                onClick = { navHostController.navigate("agregarNueva") },
=======
                onClick = { navHostController.navigate("AgregarNTarea") },
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                shape = RoundedCornerShape(1.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(text = stringResource(R.string.agregar_nueva), color = Color.Black)
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
        Text(text = tarea,Modifier.align(Alignment.CenterVertically))
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
<<<<<<< HEAD
                contentDescription = "Icono de búsqueda"
=======
                contentDescription = stringResource(R.string.icono_de_b_squeda)
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
<<<<<<< HEAD
            focusedBorderColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.surface,
        ),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
=======
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Gray,
        )
>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
    )
}