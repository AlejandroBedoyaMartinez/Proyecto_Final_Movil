package com.example.inventory.ui.nota

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.inventory.ui.tarea.BuscarText
import com.example.inventory.R
import com.example.inventory.dataNota.Nota


@Composable
fun notasScreen(navHostController: NavHostController, viewModelNota: viewModelNota) {
    val notas by viewModelNota.notas.collectAsState()
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 600

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp, 10.dp)//
                .fillMaxSize()
                .padding(10.dp, 10.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row (Modifier.padding(top = 25.dp)) {
                BuscarText(query = viewModelNota.query.value, onQueryChanged = { viewModelNota.query.value = it })
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notas.size){
                    NotaCuadro(
                        nota = notas[it],
                        onEditClick = {
                            val noteId = notas[it].id
                            navHostController.navigate("editarNota/$noteId")
                        },
                        onDeleteClick = {
                            viewModelNota.deleteNota(viewModelNota.notas.value[it])
                        },
                        navHostController = navHostController
                    )
                }
            }
        }

        Button(
            onClick = { navHostController.navigate("agregarNueva") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(40.dp)
                .zIndex(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(1.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text(text = stringResource(R.string.agregar_nueva), color = Color.Black)
        }
    }
}


@Composable
fun NotaCuadro(
    nota: Nota,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    navHostController: NavHostController
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary)
            .border(BorderStroke(1.dp, Color.Black))
            .padding(16.dp)
            .fillMaxWidth()
            .clickable
            {
                val noteId = nota.id
                navHostController.navigate("verNota/$noteId")
            }
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = nota.titulo,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = nota.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones",
                    tint = Color.Black
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.LightGray),
                    offset = DpOffset(x = -70.dp, y = (-135).dp)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onEditClick()
                        },
                        text = {
                            Text(stringResource(R.string.editar), style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onDeleteClick()
                        },
                        text = {
                            Text(stringResource(R.string.eliminar), style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
                        }
                    )
                }
            }
        }
    }
}

