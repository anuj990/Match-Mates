package com.example.matchmates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginui.SignUpScreen
import com.example.matchmates.Screens.HomeScreen
import com.example.matchmates.Screens.ProfileScreen
import com.example.matchmates.Screens.RegistrationScreen
import com.example.matchmates.Screens.LoginScreen
import com.example.editprofile.EditProfileScreen
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
                val repository = ProfileRepository()
                val authViewModel: AuthViewModel = viewModel()
                val profileViewModel: ProfileViewModel = viewModel()
                val navController = rememberNavController()

                var startDestination by remember { mutableStateOf<String?>(null) }
                val scope = rememberCoroutineScope()

                LaunchedEffect(auth.currentUser) {
                    if (auth.currentUser == null) {
                        startDestination = "SignUp"
                    } else {
                        scope.launch {
                            val exists = repository.isProfileExists(auth.currentUser!!.uid)
                            startDestination = if (exists) "HomeScreen" else "Registration"
                        }
                    }
                }

                if (startDestination == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    NavHost(
                        navController = navController,
                        startDestination = startDestination!!
                    ) {
                        composable("SignUp") { SignUpScreen(authViewModel, profileViewModel, navController) }
                        composable("SignUpScreen") { SignUpScreen(authViewModel, profileViewModel, navController) }
                        composable("Registration") { RegistrationScreen(viewModel = profileViewModel, navController = navController) }
                        composable("RegistrationSkillScreen") { RegistrationScreen(viewModel = profileViewModel, navController = navController) }
                        composable("LoginScreen") { LoginScreen(authViewModel, navController) }
                        composable("HomeScreen") { HomeScreen(navController) }
                        composable("ProfileScreen") { ProfileScreen(navController) }
                        composable("EditProfileScreen") { EditProfileScreen(navController) }
                        composable("GroupsScreen") { Text("Groups Screen") }
                        composable("ChatScreen") { Text("Chat Screen") }
                    }
                }
            }
        }
    }
}
