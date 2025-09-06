package com.example.matchmates.data

data class Profile(
    var name: String = "",
    var username: String = "",
    var skills: List<String> = emptyList(),
    var otherSkill: String = "",
    var goal: String? = null
)
