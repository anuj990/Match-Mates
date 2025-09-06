package com.example.matchmates.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.matchmates.ViewModel.ProfileViewModel
import com.example.matchmates.data.Profile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: ProfileViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var imageSelected by remember { mutableStateOf(false) }

    val skills = listOf("Android", "Web", "ML", "UI/UX", "DSA")
    var skillExpanded by remember { mutableStateOf(false) }
    var selectedSkills by remember { mutableStateOf(setOf<String>()) }

    val goals = listOf("Win", "Learn", "Fun", "Networking")
    var goalExpanded by remember { mutableStateOf(false) }
    var selectedGoal by remember { mutableStateOf<String?>(null) }

    var otherSkill by remember { mutableStateOf("") }

    val isSaving by viewModel.isSaving.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(isSaving, errorMessage) {
        if (!isSaving && errorMessage == null && name.isNotEmpty() && username.isNotEmpty()) {
            navController.navigate("HomeScreen") {
                popUpTo("Registration") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (imageSelected) {
                    Text("Image", color = Color.White, fontSize = 16.sp)
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Icon",
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(30.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(30.dp)
            )

            ExposedDropdownMenuBox(
                expanded = skillExpanded,
                onExpandedChange = { skillExpanded = !skillExpanded }
            ) {
                OutlinedTextField(
                    value = if (selectedSkills.isEmpty()) "" else selectedSkills.joinToString(", "),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Skills") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = skillExpanded) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .menuAnchor(),
                    shape = RoundedCornerShape(30.dp)
                )

                ExposedDropdownMenu(
                    expanded = skillExpanded,
                    onDismissRequest = { skillExpanded = false }
                ) {
                    skills.forEach { skill ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = selectedSkills.contains(skill),
                                        onCheckedChange = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(skill)
                                }
                            },
                            onClick = {
                                selectedSkills =
                                    if (selectedSkills.contains(skill))
                                        selectedSkills - skill
                                    else
                                        selectedSkills + skill
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = otherSkill,
                onValueChange = { otherSkill = it },
                label = { Text("Other Skill") },
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(30.dp)
            )

            ExposedDropdownMenuBox(
                expanded = goalExpanded,
                onExpandedChange = { goalExpanded = !goalExpanded }
            ) {
                OutlinedTextField(
                    value = selectedGoal ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Goal") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = goalExpanded) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .menuAnchor(),
                    shape = RoundedCornerShape(30.dp)
                )

                ExposedDropdownMenu(
                    expanded = goalExpanded,
                    onDismissRequest = { goalExpanded = false }
                ) {
                    goals.forEach { goal ->
                        DropdownMenuItem(
                            text = { Text(goal) },
                            onClick = {
                                selectedGoal = goal
                                goalExpanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val profile = Profile(
                        name = name,
                        username = username,
                        skills = selectedSkills.toList(),
                        otherSkill = otherSkill,
                        goal = selectedGoal
                    )
                    viewModel.saveProfile(profile)
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                enabled = !isSaving
            ) {
                Text(if (isSaving) "Saving.." else "Continue")
            }

            errorMessage?.let {
                Text("Error: $it", color = Color.Red)
            }
        }
    }
}
