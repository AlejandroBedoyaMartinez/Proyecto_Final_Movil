package com.example.inventory

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.inventory.dataTarea.Tarea
import com.example.inventory.ui.nota.FullScreenImageScreen
import com.example.inventory.ui.nota.viewModelNota
import com.example.inventory.ui.tarea.PosponerTarea
import com.example.inventory.ui.tarea.ViewModelTarea
import java.io.File
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarNueva(navController: NavController, viewModelNota: viewModelNota,viewModelTarea: ViewModelTarea){

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var cameraPermissionGranted by remember { mutableStateOf(false) }
    var storagePermissionGranted by remember { mutableStateOf(false) }

    // Request permissions launcher
    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            cameraPermissionGranted = permissions[Manifest.permission.CAMERA] ?: false
            storagePermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        }
    )

    // Check permissions at startup
    LaunchedEffect(Unit) {
        cameraPermissionGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        storagePermissionGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    // Observe lifecycle to request permissions
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                cameraPermissionGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                storagePermissionGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    BackHandler {
            navController.popBackStack()
    }
    var posponer by remember { mutableStateOf(true) }


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
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.navigationBars.asPaddingValues()),
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

        val imageUriList = remember { mutableStateListOf<Uri?>() }
        val videoUriList = remember { mutableStateListOf<Uri?>() }

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
                imageUriList.add(imageUri)
            }
        )

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                if(success) imageUri = uri
                imageUriList.add(imageUri)
                hasImage = success
            }
        )

        val videoLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CaptureVideo(),
            onResult = {
                videoUriList.add(imageUri)
            }
        )
        val videoPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                imageUri = uri
                videoUriList.add(imageUri)
            }
        )

        for (uri in videoUriList) {
            if (uri != null && !uri.toString().equals("") ){
                Box(
                    Modifier
                        .width(300.dp)
                        .height(300.dp)
                ) {
                    VideoPlayer(
                        videoUri = uri!!,
                        onDelete = {
                            videoUriList.remove(uri)
                        }
                    )
                }

            }
        }
        val context = LocalContext.current


        var fullScreenImageUri by remember { mutableStateOf<Uri?>(null) }
        var isDeleteDialogVisible by remember { mutableStateOf(false) }
        var selectedImageToDelete by remember { mutableStateOf<Uri?>(null) }


        if (fullScreenImageUri != null) {
            FullScreenImageScreen(
                imageUri = fullScreenImageUri!!,
                onBack = { fullScreenImageUri = null }
            )
        } else {
            for (uri in imageUriList) {
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

        Row (
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(bottom = if (isTablet) 40.dp else 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){

            Button(
                onClick = {
                    if (cameraPermissionGranted && storagePermissionGranted) {
                        val uri = ComposeFileProvider.getImageUri(context)
                        videoLauncher.launch(uri)
                        imageUri = uri
                    } else {
                        permissionsLauncher.launch(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(
                    text = "Tomar video",
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Button(
                onClick = {
                    if (storagePermissionGranted) {
                        videoPicker.launch("video/*")
                    } else {
                        permissionsLauncher.launch(
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        )
                    }                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(
                    text = "Adjuntar video",
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }
        }
        Row (
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(bottom = if (isTablet) 40.dp else 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){

            Button(
                onClick = {
                    if (cameraPermissionGranted && storagePermissionGranted) {
                        uri = ComposeFileProvider.getImageUri(context)
                        cameraLauncher.launch(uri!!)
                    } else {
                        permissionsLauncher.launch(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        )
                    }
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
                    if (storagePermissionGranted) {
                        imagePicker.launch("image/*")
                    } else {
                        permissionsLauncher.launch(
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        )
                    }                },
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
                        var imagenes: List<String> = emptyList()

                        imagenes = imageUriList.mapNotNull { uri ->
                            uri?.let {
                                saveImageToInternalStorage(context, uri)
                            }
                        }

                        var videos: List<String> = emptyList()

                        videos = videoUriList.mapNotNull { uri ->
                            uri?.let {
                                saveVideoToInternalStorage(context, uri)
                            }
                        }
                        viewModelTarea.savedTarea(imagenes,videos)
                        viewModelNota.limpiarVariables()
                        navController.navigate("tareas") {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            val context = LocalContext.current

            Button(
                onClick = {
                    if(viewModelNota.banderaSwitch.value == true) {
                        posponer = true
                    }else if(viewModelNota.tituloVacio()){
                        var imagenes: List<String> = emptyList()

                        imagenes = imageUriList.mapNotNull { uri ->
                            uri?.let {
                                saveImageToInternalStorage(context, uri)
                            }
                        }

                        var videos: List<String> = emptyList()

                        videos = videoUriList.mapNotNull { uri ->
                            uri?.let {
                                saveVideoToInternalStorage(context, uri)
                            }
                        }
                        viewModelNota.savedNota(imagenes,videos)


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

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val fileName = "image_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    return try {
        val outputStream = file.outputStream()
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun saveVideoToInternalStorage(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val fileName = "video_${System.currentTimeMillis()}.mp4" // Cambia la extensión si es necesario
    val file = File(context.filesDir, fileName)

    return try {
        val outputStream = file.outputStream()
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


