package com.audiobooks.podcasts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.audiobooks.podcasts.model.Podcast

@Composable
fun PodcastDetailsScreen(
    podcast: Podcast,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = { onBack() }, // Callback to navigate back provided by MainActivity
            modifier = Modifier.align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Back")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PodcastDetailsScreenPreview() {
//    PodcastsTheme {
//        PodcastDetailsScreen(
//            podcast = Podcast(
//                title = "Example Podcast Title",
//                description="The Ed Mylett Show showcases the greatest peak-performers across all industries in one place",
//                id="abc",
//                image="https://cdn-images-3.listennotes.com/podcasts/the-ed-mylett-show-ed-mylett-cumulus-guxpvEVnHTJ-PEUIT9RBhZD.1400x1400.jpg",
//                publisher="Podcast Publisher"
//            )
//        )
//    }
//}