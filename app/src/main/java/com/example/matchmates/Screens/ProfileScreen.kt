package com.example.matchmates.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen() {
    var isEditingSkills by remember { mutableStateOf(false) }
    var skillsText by remember { mutableStateOf("") }

    var isEditingGoals by remember { mutableStateOf(false) }
    var goalsText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("My Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF6200EE))
                            .padding(12.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Username",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "useremailid@gmail.com",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                IconButton(onClick = { /* Edit logic */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ExpandableInputSection(
            icon = Icons.Default.Accessibility,
            title = "Skills",
            isEditing = isEditingSkills,
            textValue = skillsText,
            onToggle = { isEditingSkills = !isEditingSkills },
            onValueChange = { skillsText = it },
            onSave = {
                isEditingSkills = false
            }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        ExpandableInputSection(
            icon = Icons.Default.Star,
            title = "Goals",
            isEditing = isEditingGoals,
            textValue = goalsText,
            onToggle = { isEditingGoals = !isEditingGoals },
            onValueChange = { goalsText = it },
            onSave = {
                isEditingGoals = false
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Logout logic */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out", color = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))


    }
}

@Composable
fun ExpandableInputSection(
    icon: ImageVector,
    title: String,
    isEditing: Boolean,
    textValue: String,
    onToggle: () -> Unit,
    onValueChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column {
        ProfileOption(
            icon = icon,
            title = title,
            onClick = onToggle
        )

        if (isEditing) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = textValue,
                    onValueChange = onValueChange,
                    label = { Text("Enter your $title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onSave,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save")
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        } else if (textValue.isNotBlank()) {
            Text(
                text = "Your $title: $textValue",
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 32.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun ProfileOption(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(
            Icons.Default.ArrowForwardIos,
            contentDescription = "Arrow",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}
