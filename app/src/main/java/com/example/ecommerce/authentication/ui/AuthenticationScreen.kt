package com.example.ecommerce.authentication.ui

import com.example.ecommerce.R
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce.authentication.domain.modules.LoginRequest
import com.example.ecommerce.navigation.NavScreens

@Composable
fun AuthenticationScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authUiState = authViewModel.authUiState.collectAsState().value

    LoginUi { loginRequest ->
        authViewModel.login(loginRequest)
    }

    when (authUiState) {
        is AuthUiState.Loading -> {
            Toast.makeText(context, "Loading!, Please wait", Toast.LENGTH_SHORT).show()
        }

        is AuthUiState.Error -> {
            Toast.makeText(context, "ERROR, ${authUiState.message}", Toast.LENGTH_SHORT).show()
        }

        is AuthUiState.Success -> {
            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
            navController.navigate(NavScreens.UserDetails.route)
        }
    }

}

@Composable
fun LoginUi(
    onClickLoginButton: (LoginRequest) -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Spacer(Modifier.height(120.dp))
        }

        item {
            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                label = {
                    Text(text = "User Name")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        Log.d("tag", "keyboard action invoke")
                    }
                )
            )

            if (showError) {
                Text(
                    text = "Please enter a valid email",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
        }


        item {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = "Password")
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),

                trailingIcon = {
                    val passwordIcon = if (passwordVisibility) {
                        painterResource(id = R.drawable.hidepassword)
                    } else {
                        painterResource(id = R.drawable.showpassword)
                    }

                    IconButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        },
                        content = {
                            Icon(
                                painter = passwordIcon,
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                            )
                        }
                    )

                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        Log.d("tag", "keyboard action invoke")
                    }
                )
            )
            if (showError) {
                Text(
                    text = "Please enter a strong password",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
        }

        item {
            Button(
                onClick = {
                    if (validateUserName(userName) && validatePassword(password)) {
                        onClickLoginButton.invoke(LoginRequest(userName, password))
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Login")
            }
        }

        item {
            Spacer(Modifier.height(120.dp))
        }
    }
}


fun validateUserName(userName: String): Boolean {
    return userName.isNotEmpty()
}

fun validatePassword(password: String): Boolean {
    if (password.length >= 8) {
        return true
    } else {
        return false
    }
}