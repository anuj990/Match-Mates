package com.example.matchmates.chat


import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ChatApp() }
    }
}

@Composable
fun ChatApp() {
    val navController = rememberNavController()
    val currentUserId = "user_123"

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
