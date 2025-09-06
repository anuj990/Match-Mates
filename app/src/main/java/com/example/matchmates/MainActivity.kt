package com.example.matchmates

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.loginui.SignUpScreen
import com.example.matchmates.Screens.HomeScreen
import com.example.matchmates.Screens.LoginScreen
import com.example.matchmates.Screens.RegistrationScreen
import com.example.matchmates.Screens.SearchScreen
import com.example.matchmates.ui.theme.MatchMatesTheme
import com.example.matchmates.ViewModel.AuthViewModel
import com.example.matchmates.ViewModel.ProfileViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel = AuthViewModel()
    private val profileViewModel = ProfileViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatchMatesTheme {
                MatchMatesNavigation(authViewModel, profileViewModel)
            }
        }
    }
}



