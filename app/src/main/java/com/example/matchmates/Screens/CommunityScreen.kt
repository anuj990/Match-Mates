package com.example.matchmates.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchmates.R

data class Hackathon(
    val title: String,
    val company: String,
    val location: String,
    val date: String,
    val image: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen() {
    var searchText by remember { mutableStateOf("") }

    val hackathons = listOf(
        Hackathon("Adobe India Hackathon", "Adobe India", "Online mode","15th Sept 2025", R.drawable.pic1),
        Hackathon("HackIndia 2025", "Singularity", "Offline (Regional)", "4th Oct 2025", R.drawable.pic2),
        Hackathon("HSBC", "HSBC Cooperation", "Hyderabad", "12th Sept 2025", R.drawable.pic3),
        Hackathon("Smart India Hackathon", "Government of India", "Online", "28th Sept 2025", R.drawable.pic4),
        Hackathon("5G Innovation Hackathon", "Automation Technologies", "Online","6th Sept 2025", R.drawable.pic5),
        Hackathon("Hack4Bengal 4.0", "MLH", "Offline","5th Dec 2025", R.drawable.pic6),
        Hackathon("Angels Hack Series", "Angel Hack", "Hybrid","15th Nov 2025", R.drawable.pic7),
        Hackathon("ISRO Hackathon", "ISRO", "Online","16th Dec 2025", R.drawable.pic8),
        Hackathon("Ninja Hacks", "Africa Hacks", "Offline","5th Jan 2026", R.drawable.pic9)
    )

    val filteredHackathons = hackathons.filter {
        it.title.contains(searchText, ignoreCase = true) ||
                it.company.contains(searchText, ignoreCase = true) ||
                it.location.contains(searchText, ignoreCase = true)
    }

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hackathons Community", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF3F51B5))
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search hackathons...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredHackathons) { hackathon ->
                    HackathonCard(hackathon)
                }
            }
        }
    }
}

@Composable
fun HackathonCard(hackathon: Hackathon) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = hackathon.image),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(hackathon.title, fontSize = 16.sp, color = Color(0xFF3F51B5))
                Text("${hackathon.company}, ${hackathon.location}", fontSize = 14.sp, color = Color.Gray)
                Text(hackathon.date, fontSize = 12.sp, color = Color.Gray)
            }

            Button(
                onClick = { /* TODO: apply action */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4634F3))
            ) {
                Text("Apply Now", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}
