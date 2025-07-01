package com.example.myfirstecommercekt.ui.screens.profile

import android.net.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.ui.components.*
import com.example.myfirstecommercekt.viewmodel.*


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
        verticalArrangement = Arrangement.Center
    ) {
        //AppTitle()
        Spacer(modifier = Modifier.padding(16.dp))
        Text("Tu perfil")
        Spacer(modifier = Modifier.padding(20.dp))
        //LoadImage(url = image, contentDescription = "Profile photo")
        // Spacer(modifier = Modifier.padding(8.dp))
        // UpdateImage(onClick = { launcher.launch("image/*") })
        SimpleText(
            value = name,
            onChange = { viewModel.onRegisterChange(it, email, password, confirmPassword) },
            label = "Nombre completo",
            enabled = edit, error = false

        )
        Spacer(modifier = Modifier.padding(16.dp))
        EmailField(
            email,
            enabled = false,
            onValueChange = { viewModel.onRegisterChange(name, it, password, confirmPassword) },
            error = false
        )
        Spacer(modifier = Modifier.padding(16.dp))
        if (edit) Text(
            "La constraseña debe tener más de 8 caracteres, e incluir una mayúscula, un número y un símbolo.",
            fontSize = 10.sp
        )
        PasswordField(
            password,
            enabled = edit,
            onValueChange = { viewModel.onRegisterChange(name, email, it, confirmPassword) },
            label = "Nueva contraseña",
            error = false
        )

        Spacer(modifier = Modifier.padding(16.dp))
        if (edit) PasswordField(
            confirmPassword,
            { viewModel.onRegisterChange(name, email, password, it) },
            label = "Confirmar contraseña", error = false
        )

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
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Actualizar")
    }
}

@Composable
fun UpdateImage(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Seleccionar imagen")
    }
}


