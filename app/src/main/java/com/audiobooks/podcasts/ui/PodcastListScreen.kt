package com.audiobooks.podcasts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast

@Composable
fun PodcastListScreen(
    viewModel: PodcastListViewModel = viewModel(),
    onShowDetails: (podcast: Podcast) -> Unit) {

    // Trigger fetching podcasts when the Composable is first launched
    LaunchedEffect(Unit) {
        viewModel.fetchPodcastFromAPI()
    }

    // Observe the list of podcasts and update the UI when it changes
    val podcasts by remember { derivedStateOf { viewModel.podcasts } }

    PodcastListUI(
        podcasts = podcasts,
        onShowDetails = onShowDetails
    )
}

@Composable
private fun PodcastListUI(
    podcasts: List<Podcast>,
    onShowDetails: (podcast: Podcast) -> Unit
) {
    // Column is a layout composable that places its children in a vertical sequence
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        // "Podcasts"
        Text(
            text = stringResource(R.string.Podcasts),
            style = MaterialTheme.typography.headlineLarge
        )

        // Recycler View in Compose
        LazyColumn(
            modifier = Modifier.weight(1f) // Ensures it takes available space
        ) {
            items(podcasts) { podcast ->
                PodcastCard(
                    podcast = podcast,
                    onClick = { onShowDetails(podcast) }
                )
            }
        }
    }
}

@Composable
fun PodcastCard(
    podcast: Podcast,
    onClick: (Podcast) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick (podcast) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                painter = rememberAsyncImagePainter(podcast.image), // Load image from URL
                contentDescription = "Podcast Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = podcast.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = podcast.publisher,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
