package com.example.inventory.ui.nota

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.inventory.ComposeFileProvider
import com.example.inventory.R
import com.example.inventory.VideoPlayer
import com.example.inventory.saveImageToInternalStorage
import java.io.File
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editarNota(navController: NavController, viewModelNota: viewModelNota, id:Int){

    LaunchedEffect(id) {
        viewModelNota.getNota(id)
    }

    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val islandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTablet = configuration.screenWidthDp > 600


    val textfieldWidth = when {
        isTablet && islandscape -> 0.50f
        isTablet -> 0.6f
        islandscape ->0.8f
        else -> 1f
    }

    val textFieldHeightSmall = 100.dp
    val textFieldHeightLarge = 350.dp
    val textFieldPadding = 3.dp


    Column (
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background),
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
            value = viewModelNota.titulo.value, onValueChange = {
                viewModelNota.titulo.value = it},
            placeholder = { Text(text = stringResource(R.string.ingrese_el_t_tulo)) },
            modifier = Modifier
                .fillMaxWidth(textfieldWidth)
                .height(textFieldHeightSmall)
                .padding(textFieldPadding)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.surface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .size(10.dp))




        if(islandscape) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {

                Column(
                    Modifier
                        .weight(0.4f)
                        .padding(end = 16.dp)
                ) {
                    TextField(
                        value = viewModelNota.descripcion.value,
                        onValueChange = { viewModelNota.descripcion.value = it },
                        placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
                        modifier = Modifier
                            .fillMaxWidth(textfieldWidth)
                            .height(textFieldHeightSmall)
                            .padding(textFieldPadding)
                            .background(Color.White)
                            .border(BorderStroke(1.dp, Color.Black)),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.surface,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Column(
                    Modifier
                        .weight(0.4f)
                        .padding(end = 16.dp)
                ) {
                    TextField(
                        value = viewModelNota.descripcionCuerpo.value,
                        onValueChange = { viewModelNota.descripcionCuerpo.value = it },
                        placeholder = { Text(text = stringResource(R.string.cuerpo)) },
                        modifier = Modifier
                            .fillMaxWidth(textfieldWidth)
                            .height(textFieldHeightSmall)
                            .padding(textFieldPadding)
                            .height(250.dp)
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


                }
            }
        }else {
            Spacer(modifier = Modifier.size(10.dp))

            TextField(
                value = viewModelNota.descripcion.value,
                onValueChange = { viewModelNota.descripcion.value = it },
                placeholder = { Text(text = stringResource(R.string.ingrese_la_descripcion)) },
                modifier = Modifier
                    .fillMaxWidth(textfieldWidth)
                    .height(textFieldHeightSmall)
                    .padding(textFieldPadding)
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
                    .height(250.dp)
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
            val imageUriListNuevas = remember { mutableStateListOf<Uri?>() }
            LaunchedEffect(viewModelNota.nota.imagenes) {
                imageUriList.clear()
                imageUriList.addAll(viewModelNota.nota.imagenes.map { Uri.parse(it) })
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
                        if (uri != null) {
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

            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(bottom = if (isTablet) 60.dp else 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                        if (viewModelNota.tituloVacio()) {
                            val nuevasImagenesGuardadas: List<String> = imageUriListNuevas.mapNotNull { uri ->
                                uri?.let {
                                    saveImageToInternalStorage(context, it)
                                }
                            }

                            val imagenesGuardadas: List<String> = imageUriList.mapNotNull { uri ->
                                uri?.toString()
                            }
                            val todasLasImagenes = (imagenesGuardadas + nuevasImagenesGuardadas).toSet().toList()
                            viewModelNota.editNota(todasLasImagenes)

                            viewModelNota.limpiarVariables()
                            imageUriList.clear()
                            imageUriListNuevas.clear()
                            navController.navigate("notas")
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
}

@Composable
fun FullScreenImageScreen(imageUri: Uri, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .systemBarsPadding()
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = "Full size image",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clickable { onBack() }
        )
    }
}
