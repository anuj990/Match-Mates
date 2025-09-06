package com.example.matchmates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

