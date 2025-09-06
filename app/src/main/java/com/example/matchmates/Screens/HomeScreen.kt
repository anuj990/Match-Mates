package com.example.matchmates.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.matchmates.ViewModel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val profiles = listOf(
        HardcodedProfile("Alice", "alice123", listOf("Android", "UI/UX"), "Kotlin", "Learn"),
        HardcodedProfile("Bob", "bob456", listOf("Web", "DSA"), "React", "Win"),
        HardcodedProfile("Charlie", "charlie789", listOf("ML", "DSA"), "Python", "Fun"),
        HardcodedProfile("Diana", "diana321", listOf("Android", "Web"), "Flutter", "Networking"),
        HardcodedProfile("Eve", "eve654", listOf("UI/UX", "Web"), "Figma", "Learn"),
        HardcodedProfile("Frank", "frank987", listOf("DSA", "ML"), "C++", "Win"),
        HardcodedProfile("Grace", "grace111", listOf("Web", "UI/UX"), "JavaScript", "Fun")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Match Mates") },
                actions = {
                    IconButton(onClick = { navController.navigate("SearchScreen") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }

                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = BottomAppBarDefaults.containerColor) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigate("HomeScreen") }) {
                        Icon(Icons.Default.Home, contentDescription = null)
                    }
                    IconButton(onClick = { navController.navigate("CommunityScreen") }) {
                        Icon(Icons.Default.Groups, contentDescription = null)
                    }


                    IconButton(onClick = { navController.navigate("friends") }) {
                        Icon(Icons.Default.Chat, contentDescription = null)
                    }
                    IconButton(onClick = { navController.navigate("ProfileScreen") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null)
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
                Text("Suggested Profiles:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(profiles) { profile ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { /* TODO: handle click */ },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(profile.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Username: ${profile.username}")
                            Text("Skills: ${profile.skills.joinToString(", ")}")
                            if (profile.otherSkill.isNotEmpty()) {
                                Text("Other Skills: ${profile.otherSkill}")
                            }
                            Text("Goal: ${profile.goal}")
                        }
                    }
                }
            }
        }
    }
}
