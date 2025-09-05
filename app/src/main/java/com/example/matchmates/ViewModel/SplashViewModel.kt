package com.example.matchmates.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmates.data.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: ProfileRepository = ProfileRepository()
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val user = auth.currentUser
            if (user == null) {
                _startDestination.value = "Registration"
            } else {
                val exists = repository.isProfileExists(user.uid)
                _startDestination.value = if (exists) "Home" else "Registration"
            }
        }
    }
}
