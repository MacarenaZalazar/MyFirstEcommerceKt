package com.example.toramarket.ui.screens.profile

import android.*
import android.net.*
import android.os.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.core.content.*
import androidx.hilt.navigation.compose.*
import coil.compose.*
import com.example.toramarket.ui.*
import java.io.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), toSplash: () -> Unit) {
    val state = viewModel.uiState

    LaunchedEffect(Unit) { viewModel.getUser() }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(topBar = { CenterAlignedTopAppBar({ Text("Mi Perfil") }) }) { it ->
        when (state) {
            is UIState.Loading -> {
                CircularProgressIndicator()
            }

            is UIState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(state.message, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.padding(16.dp))
                    LogoutButton { viewModel.logOut(toSplash) }
                }
            }

            is UIState.Success -> {
                Column(
                    Modifier
                        .padding(it)
                        .padding(16.dp)
                ) {
//                    ProfileImage(viewModel, snackbarHostState)
                    ProfileForm(viewModel) { toSplash }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImage(viewModel: ProfileViewModel, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var permissionsGranted by remember { mutableStateOf(false) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val image by viewModel.image.collectAsState()

    // Launchers
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { viewModel.uploadImage(it) }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && photoUri != null) {
                viewModel.uploadImage(photoUri!!)
            }
        }
    )

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            permissionsGranted = result.all { it.value }
            if (permissionsGranted) {
                showSheet = true
            }
        }
    )

    // Solicitar permisos al inicio o bajo demanda
    fun requestPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        permissionLauncher.launch(permissions)
    }

    // Crear archivo temporal para la cámara
    fun createImageFile(): Uri {
        val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    // UI
    Row(
        modifier = Modifier
            .padding(16.dp)
    ) {
        if (image.isNotEmpty()) {
            AsyncImage(
                model = image,
                contentDescription = "Imagen subida",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }


        Button(onClick = { requestPermissions() }) {
            Text("Subir imagen")
        }


    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Seleccionar origen", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            showSheet = false
                            galleryLauncher.launch("image/*")
                        }) {
                    Icon(imageVector = Icons.Outlined.Photo, contentDescription = "Galería")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Elegir desde galería")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            showSheet = false
                            val uri = createImageFile()
                            photoUri = uri
                            cameraLauncher.launch(uri)
                        }) {
                    Icon(imageVector = Icons.Outlined.CameraAlt, contentDescription = "Tomar foto")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tomar una foto")
                }
            }
        }
    }
}




