package com.example.matchmates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.loginui.SignUpScreen
import com.example.matchmates.Screens.HomeScreen
import com.example.matchmates.Screens.RegistrationScreen
import com.example.matchmates.ViewModel.AuthViewModel
import com.example.matchmates.ViewModel.ProfileViewModel
import com.example.matchmates.data.ProfileRepository
import com.example.matchmates.ui.theme.MatchMatesTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchMatesTheme {
                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser
                val repository = ProfileRepository()

                var destination by remember { mutableStateOf<String?>(null) }
                val scope = rememberCoroutineScope()

                LaunchedEffect(user) {
                    if (user == null) {
                        destination = "SignUp"
                    } else {
                        scope.launch {
                            val exists = repository.isProfileExists(user.uid)
                            destination = if (exists) "Home" else "Registration"
                        }
                    }
                }

                when (destination) {
                    null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    "SignUp" -> SignUpScreen(
                        authViewModel = AuthViewModel(),
                        profileViewModel = ProfileViewModel(),
                        onNavigateToRegistration = { destination = "Registration" }
                    )
                    "Registration" -> RegistrationScreen(viewModel = ProfileViewModel())
                    "Home" -> HomeScreen()
                }
            }
        }
    }
}
