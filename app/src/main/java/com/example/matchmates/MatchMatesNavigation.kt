package com.example.matchmates

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginui.SignUpScreen
import com.example.matchmates.Screens.HomeScreen
import com.example.matchmates.Screens.ProfileScreen
import com.example.editprofile.EditProfileScreen
import com.example.matchmates.chat.FriendListScreen
import com.example.matchmates.chat.ChatScreen
import com.example.matchmates.ViewModel.AuthViewModel
import com.example.matchmates.ViewModel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.matchmates.Screens.CommunityScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.matchmates.Screens.LoginScreen
import com.example.matchmates.Screens.RegistrationScreen
import com.example.matchmates.Screens.SearchScreen
import com.example.matchmates.chat.makeChatId

@Composable
fun MatchMatesNavigation(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()

    val startDestination = remember {
        if (FirebaseAuth.getInstance().currentUser != null) {
            "HomeScreen"
        } else {
            "SignUpScreen"
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("LoginScreen") {
            LoginScreen(authViewModel, navController)
        }

        composable("SignUpScreen") {
            SignUpScreen(authViewModel, profileViewModel, navController)
        }

        composable("Registration") {
            RegistrationScreen(profileViewModel, navController)
        }

        composable("HomeScreen") {
            HomeScreen(navController, profileViewModel)
        }

        composable("ProfileScreen") {
            ProfileScreen(navController)
        }

        composable("EditProfileScreen") {
            EditProfileScreen(navController)
        }
        composable("SearchScreen") {
            SearchScreen(navController)
        }
        composable("CommunityScreen") {
            CommunityScreen()
        }




        composable("friends") {
            FriendListScreen(
                onFriendClick = { friend ->
                    navController.navigate("chat/${friend.username}/${Uri.encode(friend.name)}")
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
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
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




