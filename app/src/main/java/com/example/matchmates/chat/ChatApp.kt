package com.example.matchmates.chat

import androidx.compose.runtime.Composable
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.matchmates.data.Profile
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatAppUi() {
    val navController = rememberNavController()
    val currentUserId = "user_123" // ✅ Hardcoded

    NavHost(navController = navController, startDestination = "friends") {
        composable("friends") {
            FriendListScreen(
                onFriendClick = { friend ->
                    navController.navigate(
                        "chat/${friend.username}/${Uri.encode(friend.name)}"
                    )
                }
            )
        }

        composable(
            route = "chat/{friendId}/{friendName}",
            arguments = listOf(
                navArgument("friendId") { type = NavType.StringType },
                navArgument("friendName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString("friendId")!!
            val friendName = Uri.decode(backStackEntry.arguments?.getString("friendName")!!)
            val chatId = makeChatId(currentUserId, friendId)

            ChatScreen(
                chatId = chatId,
                currentUserId = currentUserId,
                friendName = friendName,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
