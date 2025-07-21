package com.example.toramarket.ui.screens.profile

import android.Manifest
import android.content.*
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
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import coil.compose.*
import com.example.toramarket.R
import com.example.toramarket.utils.helpers.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImage(viewModel: ProfileViewModel) {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var permissionsGranted by remember { mutableStateOf(false) }
    val photoUri = remember { mutableStateOf<Uri?>(null) }

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
                viewModel.uploadImage(uri)

            }
        }
    }

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
                    contentDescription = stringResource(R.string.imagen_subida),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = stringResource(R.string.imagen_subida),
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
            Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16))) {
                Text(
                    stringResource(R.string.seleccionar_origen),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16)))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            requestGalleryPermission(context, {
                                showSheet = false
                                galleryLauncher.launch("image/*")
                            }, requestPermissionsLauncher)
                        }) {
                    Icon(
                        imageVector = Icons.Outlined.Photo,
                        contentDescription = stringResource(R.string.galer_a)
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_8)))
                    Text(stringResource(R.string.elegir_desde_galer_a))
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8)))

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
                    Icon(
                        imageVector = Icons.Outlined.CameraAlt, contentDescription = stringResource(
                            R.string.tomar_foto
                        )
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_8)))
                    Text(stringResource(R.string.tomar_una_foto))
                }
            }
        }
    }
}
