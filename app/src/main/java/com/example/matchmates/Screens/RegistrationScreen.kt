package com.example.matchmates.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MenuAnchorType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: androidx.navigation.NavHostController) {
    var imageSelected by remember { mutableStateOf(false) }

    val skills = listOf("Android", "Web", "ML", "UI/UX", "DSA")
    var skillExpanded by remember { mutableStateOf(false) }
    var selectedSkills by remember { mutableStateOf(setOf<String>()) }

    val goals = listOf("Win", "Learn", "Fun", "Networking")
    var goalExpanded by remember { mutableStateOf(false) }
    var selectedGoal by remember { mutableStateOf<String?>(null) }

    var otherSkill by remember { mutableStateOf("") }

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

            Button(
                onClick = { imageSelected = !imageSelected },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Add Image")
            }

            ExposedDropdownMenuBox(
                expanded = skillExpanded,
                onExpandedChange = { skillExpanded = !skillExpanded }
            ) {
                OutlinedTextField(
                    value = if (selectedSkills.isEmpty()) "" else selectedSkills.joinToString(", "),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Skills", color = Color.Black) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = skillExpanded) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .exposedDropdownSize(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
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
                                    Text(skill, color = Color.Black)
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
                label = { Text("Other Skill", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            ExposedDropdownMenuBox(
                expanded = goalExpanded,
                onExpandedChange = { goalExpanded = !goalExpanded }
            ) {
                OutlinedTextField(
                    value = selectedGoal ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Goal", color = Color.Black) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = goalExpanded) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .exposedDropdownSize(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                ExposedDropdownMenu(
                    expanded = goalExpanded,
                    onDismissRequest = { goalExpanded = false }
                ) {
                    goals.forEach { goal ->
                        DropdownMenuItem(
                            text = { Text(goal, color = Color.Black) },
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
                    println("Selected Skills: $selectedSkills")
                    println("Other Skill: $otherSkill")
                    println("Selected Goal: $selectedGoal")
                    navController.navigate("HomeScreen") // navigation added here
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(navController = androidx.navigation.compose.rememberNavController())
}
