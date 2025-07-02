package com.example.myfirstecommercekt.ui.screens.profile

import android.graphics.*
import android.net.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.ui.components.*
import com.example.myfirstecommercekt.utils.helpers.*


@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            Profile(viewModel)
        }

    }
}

@Composable
fun Profile(viewModel: ProfileViewModel) {
    val context = LocalContext.current
    val image by viewModel.image.collectAsState()
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val edit by viewModel.edit.collectAsState()
    val isFormValid by viewModel.isFormValid.collectAsState()

    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri.value = uri
        uri?.let {
            viewModel.uploadImage(it, context)
        }
    }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //AppTitle()
        Spacer(modifier = Modifier.padding(16.dp))
        Text("Mi perfil")
        if (imageUri.value != null) {
            val bitmap = remember(imageUri) {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri.value!!)
                ImageDecoder.decodeBitmap(source).asImageBitmap()
            }
            Image(
                bitmap = bitmap,
                contentDescription = "Imagen seleccionada",
                modifier = Modifier.size(100.dp)
            )
        } else {
            UpdateImage(onClick = { launcher.launch("image/*") })

        }
        Spacer(modifier = Modifier.padding(20.dp))
        //LoadImage(url = image, contentDescription = "Profile photo")
        // Spacer(modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.padding(16.dp))

        SimpleText(
            value = name,
            onChange = { viewModel.onRegisterChange(it, email, password, confirmPassword) },
            label = "Nombre completo",
            enabled = edit,
            error = { validateName(it) }

        )
        Spacer(modifier = Modifier.padding(16.dp))
        EmailField(
            email,
            enabled = false,
            onValueChange = { viewModel.onRegisterChange(name, it, password, confirmPassword) },
            error = { emailError(it) })
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(
            password,
            enabled = edit,
            onValueChange = { viewModel.onRegisterChange(name, email, it, confirmPassword) },
            label = "Nueva contraseña",
            error = { passwordError(it) })

        Spacer(modifier = Modifier.padding(16.dp))
        if (edit) PasswordField(
            confirmPassword,
            { viewModel.onRegisterChange(name, email, password, it) },
            label = "Confirmar contraseña",
            error = { confirmPasswordError(password, it) })

        Spacer(modifier = Modifier.padding(16.dp))
        if (edit) {
            UpdateButton(isFormValid) { viewModel.updateProfile() }

        } else {
            EditButton() { viewModel.editProfile() }
        }
    }
}

@Composable
fun EditButton(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Editar")
    }
}

@Composable
fun UpdateButton(enabled: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), enabled = enabled) {
        Text("Actualizar")
    }
}

@Composable
fun UpdateImage(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Seleccionar imagen")
    }
}


