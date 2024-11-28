package com.example.inventory.ui.tarea

import android.Manifest
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.inventory.MainActivity
import com.example.inventory.R
import com.example.inventory.dataTarea.Tarea



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PosponerTarea(onDismiss: () -> Unit,viewModelTarea: ViewModelTarea,tarea: Tarea, onTareaGuardada: () -> Unit) {

    val context = LocalContext.current
    // ssi el cuadro de diálogo ha sido mostrado
    var isDialogShown by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permiso concedido para notificaciones.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permiso denegado para notificaciones.", Toast.LENGTH_SHORT).show()
        }
    }



    Dialog(onDismissRequest = onDismiss) {
        viewModelTarea.llenarDatos(tarea)
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var fechaInicio by remember { mutableStateOf("") }
        var fechaFin by remember { mutableStateOf("") }

        val datePickerDialogInicio = android.app.DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                fechaInicio = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            }, year, month, day
        )

        val datePickerDialogFin = android.app.DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                fechaFin = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            }, year, month, day
        )



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
                    Button(
                        onClick = { datePickerDialogInicio.show() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Icono calendario",
                                tint = MaterialTheme.colorScheme.surface
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (fechaInicio.isEmpty()) stringResource(R.string.elegir_fecha) else fechaInicio,
                                color = MaterialTheme.colorScheme.surface,
                                fontSize = 13.5.sp
                            )
                        }
                    }
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
                    Button(
                        onClick = { datePickerDialogFin.show() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Icono calendario",
                                tint = MaterialTheme.colorScheme.surface
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = if (fechaFin.isEmpty()) stringResource(R.string.elegir_fecha) else fechaFin,
                                color = MaterialTheme.colorScheme.surface,
                                fontSize = 13.5.sp
                            )
                        }
                    }

                }


                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    Checkbox(
                        checked = viewModelTarea.recordar.value,
                        onCheckedChange = { checked ->
                            viewModelTarea.recordar.value = checked
                            if (checked) {
                                isDialogShown = true
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "No se requiere permiso para notificaciones en esta versión.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }


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
                        onClick =
                        {
                            if(viewModelTarea.tituloVacio()){
                                if(fechaInicio !="" && fechaFin != ""){
                                    viewModelTarea.fechaInicio.value = fechaInicio
                                    viewModelTarea.fechaFin.value = fechaFin
                                }

                                viewModelTarea.savedTarea()
                                onTareaGuardada()
                            }else{
                                Toast.makeText(context,
                                    context.getString(R.string.escribe_un_titulo), Toast.LENGTH_SHORT).show()
                            }
                            onDismiss()
                        },
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

