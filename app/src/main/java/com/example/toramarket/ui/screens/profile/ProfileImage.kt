package com.example.toramarket.ui.screens.profile

import android.*
import android.content.*
import android.content.pm.*
import android.net.*
import android.os.*
import android.util.*
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
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import coil.compose.*
import com.example.toramarket.utils.helpers.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImage(viewModel: ProfileViewModel, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var permissionsGranted by remember { mutableStateOf(false) }
    val photoUri = remember { mutableStateOf<Uri?>(null) }

    val edit by viewModel.edit.collectAsState()

    val image by viewModel.image.collectAsState()

    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionsGranted = permissions.values.all { it }
    }


    fun requestCameraPermission(
        context: Context,
        onGranted: () -> Unit,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    ) {
        val permissions = mutableListOf(Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }


        val allGranted = permissions.all {
            context.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }

        if (allGranted) {
            onGranted()
        } else {
            launcher.launch(permissions.toTypedArray())
        }
    }


    fun requestGalleryPermission(
        context: Context,
        onGranted: () -> Unit,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
    ) {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            onGranted()
        } else {
            launcher.launch(arrayOf(permission))
        }
    }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.uploadImage(it)
        }
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUri.value?.let { uri ->
                Log.d("PHOTO_URI", "Launching camera with URI: ${photoUri.value}")
                viewModel.uploadImage(uri)

            }
        }
    }

    if (!edit) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = { showSheet = true }),
                contentAlignment = Alignment.Center
            ) {
                if (image.isNotEmpty()) {
                    AsyncImage(
                        model = image,
                        modifier = Modifier
                            .size(100.dp),
                        contentDescription = "Imagen subida", contentScale = ContentScale.Crop
                    )
                } else {
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
                            requestGalleryPermission(context, {
                                showSheet = false
                                galleryLauncher.launch("image/*")
                            }, requestPermissionsLauncher)
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
                            val uri = createImageFile(context)
                            photoUri.value = uri
                            requestCameraPermission(
                                context = context,
                                onGranted = {
                                    photoUri.value?.let { cameraLauncher.launch(it) }
                                },
                                launcher = requestPermissionsLauncher,
                            )

                        }) {
                    Icon(imageVector = Icons.Outlined.CameraAlt, contentDescription = "Tomar foto")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tomar una foto")
                }
            }
        }
    }
}
