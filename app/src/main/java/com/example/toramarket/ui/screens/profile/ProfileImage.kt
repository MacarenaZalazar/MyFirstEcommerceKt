package com.example.toramarket.ui.screens.profile

import android.*
import android.content.pm.*
import android.net.*
import android.os.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import coil.compose.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImage(viewModel: ProfileViewModel, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var permissionsGranted by remember { mutableStateOf(false) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val image by viewModel.image.collectAsState()

    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionsGranted = permissions.values.all { it }
        if (!permissionsGranted) {
//            snackbarHostState.showSnackbar("Permisos necesarios denegados")
        }
    }

    // Check and request permissions
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsGranted =
                context.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            permissionsGranted =
                context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }

        if (!permissionsGranted) {
            val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            requestPermissionsLauncher.launch(permissions)
        }
    }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.uploadImage(it)
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (image.isNotEmpty()) {
            AsyncImage(
                model = image,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentDescription = "Imagen subida",
            )
        } else {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = { showSheet = true }),

                contentAlignment = Alignment.Center
            ) {

                Image(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Imagen subida",
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier
                        .size(60.dp)

                )
            }
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

//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .clickable {
//                            showSheet = false
//                            val uri = createImageFile()
//                            photoUri = uri
//                            cameraLauncher.launch(uri)
//                        }) {
//                    Icon(imageVector = Icons.Outlined.CameraAlt, contentDescription = "Tomar foto")
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text("Tomar una foto")
//                }
            }
        }
    }
}
