package com.example.matchmates.chat


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matchmates.data.Profile

@Composable
fun FriendListScreen(onFriendClick: (Profile) -> Unit) {
    val friends = listOf(
        Profile(name = "Anuj", username = "anuj_99"),
        Profile(name = "Rohan", username = "rohan_123"),
        Profile(name = "Nima", username = "nima_gajmoti"),
        Profile(name = "Aditi", username = "aditi_07")
    )

    Box(modifier = Modifier.padding(top = 50.dp)){
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {

            items(friends) { friend ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onFriendClick(friend) },
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(text = friend.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "@${friend.username}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
