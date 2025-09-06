package com.example.matchmates.chat

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Friend(
    val id : String,
    val name: String,
)


fun makeChatId(a:String,b:String): String{

    return if(a<b) "$a-$b" else "$b-$a"
}

fun formatTimestamp(ts: Long): String {
    return try {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        sdf.format(Date(ts))
    } catch (e: Exception) {
        ""
    }
}