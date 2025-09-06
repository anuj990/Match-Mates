package com.example.matchmates.ViewModel

import android.util.Log
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

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _currentProfile = MutableStateFlow<Profile?>(null)
    val currentProfile = _currentProfile.asStateFlow()

    private val _similarProfiles = MutableStateFlow<List<Profile>>(emptyList())
    val similarProfiles = _similarProfiles.asStateFlow()

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

    fun loadCurrentProfile(username: String) {
        viewModelScope.launch {
            try {
                val snapshot = repository.getProfilesByIds(listOf(username))
                val profile = snapshot.firstOrNull()
                _currentProfile.value = profile

                profile?.let {
                    Log.d("ProfileVM", "Current user skills: ${it.skills}")
                    loadSimilarProfiles(it)
                }
            } catch (e: Exception) {
                Log.e("ProfileVM", "Failed to load current profile", e)
            }
        }
    }

    private fun loadSimilarProfiles(profile: Profile) {
        viewModelScope.launch {
            try {
                val allProfiles = repository.getAllProfilesExcluding(profile.username)
                val similar = allProfiles.filter { other ->
                    other.skills.any { it in profile.skills }
                }
                Log.d("ProfileVM", "Found similar profiles: ${similar.map { it.username }}")
                _similarProfiles.value = similar
            } catch (e: Exception) {
                Log.e("ProfileVM", "Failed to load similar profiles", e)
                _similarProfiles.value = emptyList()
            }
        }
    }
}
