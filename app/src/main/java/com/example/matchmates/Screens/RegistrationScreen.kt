package com.example.matchmates.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen() {
    var imageSelected by remember { mutableStateOf(false) }

    val skills = listOf("Android", "Web", "ML", "UI/UX", "DSA")
    var skillExpanded by remember { mutableStateOf(false) }
    var selectedSkill by remember { mutableStateOf<String?>(null) }

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
            // Profile circle
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

            // Add Image button (currently just toggles placeholder)
            Button(
                onClick = { imageSelected = !imageSelected },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Add Image")
            }

            // Skill Dropdown
            ExposedDropdownMenuBox(
                expanded = skillExpanded,
                onExpandedChange = { skillExpanded = !skillExpanded }
            ) {
                OutlinedTextField(
                    value = selectedSkill ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Skill", color = Color.Black) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(0.8f),
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
                            text = { Text(skill, color = Color.Black) },
                            onClick = {
                                selectedSkill = skill
                                skillExpanded = false
                            }
                        )
                    }
                }
            }

            // Other Skill input
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

            // Goal Dropdown
            ExposedDropdownMenuBox(
                expanded = goalExpanded,
                onExpandedChange = { goalExpanded = !goalExpanded }
            ) {
                OutlinedTextField(
                    value = selectedGoal ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Goal", color = Color.Black) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(0.8f),
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

            // Continue button
            Button(
                onClick = {
                    // Example: Use selectedSkill, otherSkill, and selectedGoal
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
    RegistrationScreen()
}
