package com.example.matchmates.chat


data class Message(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

