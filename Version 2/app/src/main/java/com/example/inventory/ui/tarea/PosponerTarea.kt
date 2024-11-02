package com.example.inventory.ui.tarea

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.inventory.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PosponerTarea(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth() ,
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme. background,
            border = BorderStroke(2.dp, Color.LightGray)
        ) {
            Column(
            ) {
                Text(text = stringResource(R.string.posponer_tarea), Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .height(40.dp)
                    .padding(8.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.surface,
                        text = stringResource(R.string.nueva_fecha_de_inicio),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = stringResource(R.string.elegir_fecha),
                        onValueChange = {},
                        modifier = Modifier.weight(1f),
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Icono calendario"
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = Color.Gray,
                        ),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.surface,
                        text = stringResource(R.string.nueva_fecha_de_fin),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = stringResource(R.string.elegir_fecha),
                        onValueChange = { },
                        modifier = Modifier.weight(1f),
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Icono calendario"
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = Color.Gray,
                        ),
                    )
                }


                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = { /* TODO */ }
                    )
                    Text(text = stringResource(R.string.recordarme),Modifier.align(Alignment.CenterVertically))
                }

                Row (
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(
                        onClick = { },
                        Modifier
                            .border(BorderStroke(2.dp, Color.Black))
                        ,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.guardar),
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

