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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchmates.R

// ---------- Data Model ----------
data class Job(
    val title: String,
    val company: String,
    val location: String,
    val date: String,
    val image: Int
)

// ---------- Main Screen ----------
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JobListingScreen() {
    var searchText by remember { mutableStateOf("") }
    val jobs = listOf(
        Job("Adobe India Hackathon", "Adobe India", "Online mode","15th Sept 2025",R.drawable.pic1 ),
        Job("HackIndia 2025", "Singularity", "Offline (Regional)", "4th Oct 2025", R.drawable.pic2),
        Job("HSBC", "HSBC Cooperation", "Hyderabad", "12th Sept 2025", R.drawable.pic3),
        Job("Smart India Hackathon", "Government of India", "Online", "28th Sept 2025", R.drawable.pic4),
        Job("5G Innovation Hackathon", "Automation Technologies", "Online","6th Sept 2025", R.drawable.pic5),
        Job("Hack4Bengal 4.0", "MLH", "Offline","5th Dec 2025", R.drawable.pic6),
        Job("Angels Hack Series", "Angel Hack", "Hybrid","15th Nov 2025", R.drawable.pic7),
        Job("ISRO Hackathon", "ISRO", "Online","16th Dec 2025", R.drawable.pic8),
        Job("Ninja Hacks", "Africa Hacks", "Offline","5th Jan 2026", R.drawable.pic9),
    )

    // Filter jobs by search text
    val filteredJobs = jobs.filter {
        it.title.contains(searchText, ignoreCase = true) ||
                it.company.contains(searchText, ignoreCase = true) ||
                it.location.contains(searchText, ignoreCase = true)
    }

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Hackathon Community", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3F51B5)
                )
            )
        }
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            Column {
                // -------- Search Bar --------
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

                // -------- Job List --------
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredJobs) { job ->
                            JobCard(job)
                        }
                    }

                    // -------- Custom Scrollbar --------
                    val firstVisibleIndex = listState.firstVisibleItemIndex
                    val totalItems = filteredJobs.size
                    val scrollFraction =
                        if (totalItems > 0) firstVisibleIndex.toFloat() / totalItems.toFloat() else 0f

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                            .width(6.dp)
                            .padding(end = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp) // scrollbar thumb size
                                .align(Alignment.TopCenter)
                                .offset(y = (scrollFraction * 400).dp) // moves with scroll
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFF3F51B5))
                        )
                    }
                }
            }
        }
    }
}

// ---------- Job Card ----------
@Composable
fun JobCard(job: Job) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile / Company Logo
            Image(
                painter = painterResource(id = job.image),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(job.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF3F51B5))
                Text("${job.company}, ${job.location}", fontSize = 14.sp, color = Color.Gray)
                Text(job.date, fontSize = 12.sp, color = Color.Gray)
            }

            // Apply Button
            Button(
                onClick = { },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4634F3))
            ) {
                Text("Apply Now", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}
