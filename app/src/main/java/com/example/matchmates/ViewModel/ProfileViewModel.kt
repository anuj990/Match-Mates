package com.example.matchmates.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmates.data.Profile
import com.example.matchmates.data.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository = ProfileRepository()
) : ViewModel() {

    private val _friends = MutableStateFlow<List<Profile>>(emptyList())
    val friends = _friends.asStateFlow()

    fun loadFriends(userIds: List<String>) {
        viewModelScope.launch {
            val profiles = repository.getProfilesByIds(userIds)
            _friends.value = profiles // ✅ use Profile directly
        }
    }

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun saveProfile(profile: Profile) {
        viewModelScope.launch {
            _isSaving.value = true
            _errorMessage.value = null
            val result = repository.saveProfile(profile)
            _isSaving.value = false

            if (result.isFailure) {
                _errorMessage.value = result.exceptionOrNull()?.message
            }
        }
    }
}
