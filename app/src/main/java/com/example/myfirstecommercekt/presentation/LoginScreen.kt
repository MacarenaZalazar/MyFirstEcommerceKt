package com.example.myfirstecommercekt.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstecommercekt.viewmodel.LoginViewModel


@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel)
    }

}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {
    val email = viewModel.email.collectAsState(initial = "")

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        EmailField()
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField()
        Spacer(modifier = Modifier.padding(8.dp))
        ForgotPassword(modifier = Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.padding(16.dp))
        LoginButton()

    }
}

@Composable
fun EmailField() {
    TextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true
    )
}

@Composable
fun PasswordField() {
    TextField(
        value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true
    )
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text("¿Olvidaste tu contraseña?", modifier = modifier.clickable {})
}


@Composable
fun LoginButton() {
    Button(
        onClick = {},
    ) {
        Text("Ingresar")
    }
}


//@SuppressLint("ViewModelConstructorInComposable")
//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun LoginScreenPreview() {
//    LoginScreen(viewModel = LoginViewModel())
//}



