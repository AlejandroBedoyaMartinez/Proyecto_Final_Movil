package com.example.inventory.ui.tarea

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inventory.R
import com.example.inventory.dataTarea.Tarea


@Composable
fun tareasScreen(navHostController: NavHostController, viewModelTarea: ViewModelTarea) {
    val tareas by viewModelTarea.tareas.collectAsState()

    val configuration = LocalConfiguration.current
    val islandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTablet = configuration.screenWidthDp > 600

    val columnPadding = if (isTablet && !islandscape) 16.dp else 0.dp
    Column(
        modifier = Modifier
            .padding(columnPadding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Row (Modifier.padding(top = if (isTablet) 25.dp else 15.dp)) {
            BuscarText(query = viewModelTarea.query.value, onQueryChanged = { viewModelTarea.query.value = it })
        }


        if(isTablet) {

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
                    items(tareas.size) {
                        val color = if (it % 2 == 0) Color.LightGray else Color.White
                        GenerarTarea(
                            tarea = tareas[it],
                            colorFondo = color,
                            checked = tareas[it].hecho,
                            onCheckedChange = { check ->
                                tareas[it].hecho = check
                                viewModelTarea.editCheck(tareas[it])
                                navHostController.navigate("tareas") {
                                    popUpTo("home") { inclusive = false }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            navHostController,
                            viewModelTarea
                        )
                    }
                }
                Button(
                    onClick = { navHostController.navigate("agregarNueva") },
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(1.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(text = stringResource(R.string.agregar_nueva), color = Color.Black)
                }
            }

        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    items(tareas.size) {
                        val color = if (it % 2 == 0) Color.LightGray else Color.White
                        GenerarTarea(
                            tarea = tareas[it],
                            colorFondo = color,
                            checked = tareas[it].hecho,
                            onCheckedChange = { check ->
                                tareas[it].hecho = check
                                viewModelTarea.editCheck(tareas[it])
                            },
                            navHostController,
                            viewModelTarea
                        )
                    }
                }

                Button(
                    onClick = { navHostController.navigate("agregarNueva") },
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
    }





@Composable
fun GenerarTarea(
    tarea: Tarea,
    colorFondo: Color,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    navHostController: NavHostController,
    viewModelTarea: ViewModelTarea
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .background(colorFondo)
            .padding(5.dp)
            .clickable {
                val tareaId = tarea.id
                navHostController.navigate("verTarea/$tareaId")
            },
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = tarea.titulo,
            Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Black,
                checkmarkColor = Color.Black,
                checkedColor = Color.LightGray
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        IconButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Opciones",
                tint = Color.Black
            )
            editarEliminar(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                onEditClick = {
                    expanded = false
                    val tareaId = tarea.id
                    navHostController.navigate("editarTarea/$tareaId")
                },
                onDeleteClick = {
                    expanded = false
                    viewModelTarea.deleteTarea(tarea)
                }
            )
        }
    }
}


@Composable
fun editarEliminar(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(Color.LightGray),
        offset = DpOffset(x =-70.dp, y = (-115).dp)
    ) {
        DropdownMenuItem(
            onClick = {
                onEditClick()
            },
            text = {
                Text(
                    stringResource(R.string.editar),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray
                )
            }
        )
        DropdownMenuItem(
            onClick = {
                onDeleteClick()
            },
            text = {
                Text(
                    stringResource(R.string.eliminar),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray
                )
            }
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.surface,
        ),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )
}