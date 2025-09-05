package com.example.matchmates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginui.SignUpScreen
import com.example.matchmates.Screens.*
import com.example.matchmates.ViewModel.AuthViewModel
import com.example.matchmates.ui.theme.MatchMatesTheme

class MainActivity : ComponentActivity() {
    private val authViewModel = AuthViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchMatesTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "SignUpScreen"
                ) {
                    composable("SignUpScreen") {
                        SignUpScreen(authViewModel, navController)
                    }
                    composable("RegistrationSkillScreen") {
                        RegistrationScreen(navController)
                    }
                    composable("HomeScreen") {
                        HomeScreen(navController)
                    }
                    composable("LoginScreen") {
                        LoginScreen(authViewModel, navController)
                    }
                    composable("ProfileScreen") {
                        ProfileScreen(navController)
                    }
                    composable("EditProfileScreen") {
                        EditProfileScreen(navController)
                    }
                }
            }
        }
    }
}
