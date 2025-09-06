package com.example.matchmates.Screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    var query by remember { mutableStateOf("") }

    val profiles = listOf(
        HardcodedProfile("Alice", "alice123", listOf("Android", "UI/UX"), "Kotlin", "Learn"),
        HardcodedProfile("Bob", "bob456", listOf("Web", "DSA"), "React", "Win"),
        HardcodedProfile("Charlie", "charlie789", listOf("ML", "DSA"), "Python", "Fun"),
        HardcodedProfile("Diana", "diana321", listOf("Android", "Web"), "Flutter", "Networking"),
        HardcodedProfile("Eve", "eve654", listOf("UI/UX", "Web"), "Figma", "Learn"),
        HardcodedProfile("Frank", "frank987", listOf("DSA", "ML"), "C++", "Win"),
        HardcodedProfile("Grace", "grace111", listOf("Web", "UI/UX"), "JavaScript", "Fun")
    )

    val filteredProfiles = profiles.filter { profile ->
        profile.name.contains(query, ignoreCase = true) ||
                profile.username.contains(query, ignoreCase = true) ||
                profile.skills.any { it.contains(query, ignoreCase = true) } ||
                profile.goal.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Search Profiles") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search by name, skill, or goal") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(filteredProfiles) { profile ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { /* TODO: Open profile detail */ },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
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
}
