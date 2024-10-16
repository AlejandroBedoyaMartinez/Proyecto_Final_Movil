package jano.net.proyecto_final

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController


@Composable
fun notasScreen(navHostController: NavHostController) {
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 10.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row (Modifier.padding(top = 25.dp)) {
                BuscarText(query = query, onQueryChanged = { query = it })
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(15) {
                    val i = it + 1
                    NotaCuadro(titulo = "Nota $i", contenido = "Descripcion")
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
            Text(text = "Agregar nueva", color = Color.Black)
        }
    }
}


@Composable
fun NotaCuadro(
    titulo: String,
    contenido: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary)
            .border(BorderStroke(1.dp, Color.Black))
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = titulo, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = contenido, style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .size(24.dp)
                    .padding(0.dp)
                    .background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones",
                    tint = Color.Black
                )
            }
        }
    }
}