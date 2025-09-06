package com.example.loginui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.matchmates.ViewModel.AuthViewModel
import com.example.matchmates.ViewModel.ProfileViewModel
import com.example.matchmates.data.Profile


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.observeAsState()

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthViewModel.AuthState.Authenticated -> {
                val profile = Profile(
                    name = "",
                    username = "",
                    skills = emptyList(),
                    otherSkill = "",
                    goal = ""
                )
                profileViewModel.saveProfile(profile)
                navController.navigate("RegistrationSkillScreen") {
                    popUpTo("SignUpScreen") { inclusive = true }
                }
            }
            is AuthViewModel.AuthState.Error -> {
                errorMessage = state.message
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCFE5E3)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Sign Up",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Id") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    val image = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                trailingIcon = {
                    val image = if (showConfirmPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    when {
                        password != confirmPassword -> {
                            errorMessage = "Passwords do not match"
                        }
                        email.isEmpty() || password.isEmpty() -> {
                            errorMessage = "Email or Password can't be empty"
                        }
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                            errorMessage = "Invalid email format"
                        }
                        else -> {
                            errorMessage = null
                            authViewModel.signUp(email, password)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B3179)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up", color = Color.White)
            }

            TextButton(onClick = {
                navController.navigate("LoginScreen"){
                    popUpTo("SignupScreen") { inclusive = true }

                }
            }) {
                Text(text = "Already have an account? Login", color = Color(0xFF1A237E))
            }
        }
    }
}
