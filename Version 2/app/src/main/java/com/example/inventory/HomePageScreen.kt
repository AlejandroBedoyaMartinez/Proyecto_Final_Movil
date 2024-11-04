package com.example.inventory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.lang.reflect.Type

@Composable
fun HomePageScreen(navHostController: NavHostController) {

    BoxWithConstraints {
        val isWideScreen =
            maxWidth > 600.dp // Se considera tablet o modo horizontal si el ancho es mayor a

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier.padding(
                    top = 45.dp, start = 28.dp
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.usuario),
                    contentDescription = "ImagenUsuario",
                    modifier = Modifier.size(60.dp)
                )
                Text(
                    text = stringResource(R.string.bienvenido),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 15.dp),
                    color = MaterialTheme.colorScheme.surface
                )
            }
            Text(
                text = stringResource(R.string.pagina_de_inicio),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.titleLarge
            )


            if (isWideScreen) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {

                        Button(
                            onClick = { navHostController.navigate("tareas") },
                            modifier = Modifier
                                .width(130.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(2.dp, Color.Black)
                        ) {
                            Text(text = stringResource(R.string.tareas), color = Color.Black)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navHostController.navigate("notas") },
                            modifier = Modifier
                                .width(130.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(2.dp, Color.Black)
                        ) {
                            Text(text = stringResource(R.string.notas), color = Color.Black)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .border(BorderStroke(2.dp, Color.Gray))
                            .width(310.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.proximos_eventos_del_mes),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray)
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                FloatingActionButton(
                    onClick = { navHostController.navigate("agregarNueva") },
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(BorderStroke(2.dp, Color.Magenta), shape = CircleShape),
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.Magenta)
                }
            } else {

                // Diseño en modo horizontal en celular (ancho <= 600dp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { navHostController.navigate("tareas") },
                            modifier = Modifier.width(130.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(2.dp, Color.Black)
                        ) {
                            Text(text = stringResource(R.string.tareas), color = Color.Black)
                        }

                        Button(
                            onClick = { navHostController.navigate("notas") },
                            modifier = Modifier.width(130.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(2.dp, Color.Black)
                        ) {
                            Text(text = stringResource(R.string.notas), color = Color.Black)
                        }


                    }//row

                    Spacer(modifier = Modifier.height(32.dp))

                    // Caja de próximos eventos
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .border(BorderStroke(2.dp, Color.Gray))
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.proximos_eventos_del_mes),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray)
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize(), // Llenar todo el espacio disponible
                        contentAlignment = Alignment.Center // Centrar contenido horizontal y verticalmente
                    ) {
                        FloatingActionButton(
                            onClick = { navHostController.navigate("agregarNueva") },
                            modifier = Modifier
                                .size(60.dp)
                                .border(BorderStroke(2.dp, Color.Magenta), shape = CircleShape),
                            containerColor = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Agregar",
                                tint = Color.Magenta
                            )
                        }
                    }
                }
            }
        }
    }

    }


