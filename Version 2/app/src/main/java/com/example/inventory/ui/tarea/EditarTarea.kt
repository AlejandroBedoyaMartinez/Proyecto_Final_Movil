package com.example.inventory.ui.tarea

import android.icu.util.Calendar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inventory.ComposeFileProvider
import com.example.inventory.R
import com.example.inventory.saveImageToInternalStorage
import com.example.inventory.ui.nota.FullScreenImageScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editarTarea(navController: NavController,viewModelTarea: ViewModelTarea,id:Int){
    LaunchedEffect(id) {
        viewModelTarea.getTarea(id)
    }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val scrollState = rememberScrollState()


    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }

    fechaInicio = viewModelTarea.fechaInicio.value.toString()
    fechaFin = viewModelTarea.fechaFin.value.toString()

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

    Column (
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.navigationBars.asPaddingValues()),
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
            value = viewModelTarea.titulo.value, onValueChange = {
                viewModelTarea.titulo.value = it},
            placeholder = { Text(text = stringResource(R.string.escribe_un_titulo)) },
            modifier = Modifier
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            )
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(10.dp))
        Row (
            modifier = Modifier.width(280.dp)
        ){
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
        Spacer(modifier = Modifier.size(5.dp))
        TextField(
            value = viewModelTarea.descripcion.value, onValueChange = { viewModelTarea.descripcion.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
            modifier = Modifier
                .height(100.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = textFieldColors(
                 containerColor  = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )
        TextField(
            value = viewModelTarea.descripcionCuerpo.value, onValueChange = { viewModelTarea.descripcionCuerpo.value = it},
            placeholder = { Text(text = stringResource(R.string.cuerpo)) },
            modifier = Modifier
                .height(220.dp)
                .padding(top = 10.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )
        TextField(
            value = viewModelTarea.texto.value, onValueChange = { viewModelTarea.texto.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_el_texto)) },
            modifier = Modifier
                .height(220.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )

        val imageUriList = remember { mutableStateListOf<Uri?>() }
        val imageUriListNuevas = remember { mutableStateListOf<Uri?>() }
        LaunchedEffect(viewModelTarea.tarea.imagenes) {
            imageUriList.clear()
            imageUriList.addAll(viewModelTarea.tarea.imagenes.map { Uri.parse(it) })
        }


        var uri : Uri? = null
        // 1
        var hasImage by remember {
            mutableStateOf(false)
        }
        var hasVideo by remember {
            mutableStateOf(false)
        }
        // 2
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }


        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                hasImage = uri != null
                imageUri = uri
                imageUriListNuevas.add(imageUri)
            }
        )

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                if(success) imageUri = uri
                imageUriListNuevas.add(imageUri)
                hasImage = success
            }
        )


        var fullScreenImageUri by remember { mutableStateOf<Uri?>(null) }
        var isDeleteDialogVisible by remember { mutableStateOf(false) }
        var selectedImageToDelete by remember { mutableStateOf<Uri?>(null) }


        if (fullScreenImageUri != null) {
            FullScreenImageScreen(
                imageUri = fullScreenImageUri!!,
                onBack = { fullScreenImageUri = null }
            )
        } else {
            val todas = remember { mutableStateListOf<Uri?>() }
            todas.addAll(imageUriList)
            todas.addAll(imageUriListNuevas)
            for (uri in todas) {
                if (uri != null && !uri.toString().equals("") ){
                    Box(
                        Modifier
                            .width(230.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        isDeleteDialogVisible = true
                                        selectedImageToDelete = uri
                                    },
                                    onTap = {
                                        fullScreenImageUri = uri
                                    }
                                )
                            }
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Selected image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }

                }
            }
            todas.clear()
        }


        if (isDeleteDialogVisible) {
            AlertDialog(
                onDismissRequest = { isDeleteDialogVisible = false },
                title = {
                    Text(
                        text = "Eliminar Imagen",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                text = {
                    Text(
                        text = "¿Está seguro de eliminar la imagen seleccionada?",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        selectedImageToDelete?.let { uriToDelete ->
                            imageUriList.remove(uriToDelete)
                            imageUriListNuevas.remove(uriToDelete)
                        }
                        isDeleteDialogVisible = false
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        )
                    ) {
                        Text(
                            text = "Eliminar",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        isDeleteDialogVisible = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        )
                    ) {
                        Text(
                            text = "Cancelar" ,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.size(10.dp))
        val context = LocalContext.current
        Row (
            modifier = Modifier
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            Button(
                onClick = {
                    uri = ComposeFileProvider.getImageUri(context)
                    //imageUri = uri
                    cameraLauncher.launch(uri!!)
                },
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
                onClick = {
                    imagePicker.launch("image/*")
                },
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
                    if(viewModelTarea.tituloVacio()){
                        if(fechaInicio !="" && fechaFin != ""){
                            viewModelTarea.fechaInicio.value = fechaInicio
                            viewModelTarea.fechaFin.value = fechaFin
                        }
                        val nuevasImagenesGuardadas: List<String> = imageUriListNuevas.mapNotNull { uri ->
                            uri?.let {
                                saveImageToInternalStorage(context, it)
                            }
                        }

                        val imagenesGuardadas: List<String> = imageUriList.mapNotNull { uri ->
                            uri?.toString()
                        }
                        val todasLasImagenes = (imagenesGuardadas + nuevasImagenesGuardadas).toSet().toList()

                        viewModelTarea.editTarea(todasLasImagenes)

                        viewModelTarea.limpiarVariables()
                        imageUriList.clear()
                        imageUriListNuevas.clear()
                        navController.navigate("tareas") {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
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