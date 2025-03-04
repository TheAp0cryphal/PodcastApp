package com.audiobooks.podcasts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.Dimensions
import com.audiobooks.podcasts.ui.theme.PodcastsTheme

/**
 * This file contains the implementation of the PodcastListScreen and related composables.
 *
 * The PodcastListScreen composable is responsible for displaying a list of podcasts fetched from an API.
 * It uses a ViewModel to manage the state and fetch the data.
 *
 * The PodcastListUI composable handles the UI layout and displays a loading indicator when the data is being fetched.
 * It also includes a debounce mechanism to prevent multiple rapid clicks on podcast items.
 *
 * The PodcastCard composable displays individual podcast details in a card format.
 *
 * A preview function, PodcastListPreview, is provided to render a sample list of podcasts for UI testing.
 */

// Debounce interval in milliseconds, it's longer than the time it takes to navigate to the details screen
const val debounceInterval = 1000L


@Composable
fun PodcastListScreen(
    viewModel: PodcastListViewModel = viewModel(),
    onShowDetails: (podcast: Podcast) -> Unit) {

    // Trigger fetching podcasts when the Composable is first launched
    LaunchedEffect(Unit) {
        viewModel.fetchPodcastFromAPI()
    }

    // Observe the list of podcasts and update the UI when it changes
    val podcasts by viewModel.podcasts.collectAsStateWithLifecycle()

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
    var lastClickTime by remember { mutableLongStateOf(0L) }

    // Column is a layout composable that places its children in a vertical sequence
    Column(
        modifier = Modifier
            .padding(Dimensions.paddingLarge)
            .fillMaxSize()
    ) {
        // "Podcasts"
        Text(
            modifier = Modifier.padding(vertical = Dimensions.paddingMedium),
            text = stringResource(R.string.podcasts),
            style = MaterialTheme.typography.headlineLarge
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (podcasts.isEmpty()) {
                // Show loading indicator when the list is empty
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = Dimensions.paddingLarge)
                ) {
                    items(podcasts) { podcast ->
                        PodcastCard(
                            podcast = podcast,
                            onClick = {
                                val currentTime = System.currentTimeMillis()
                                if (currentTime - lastClickTime > debounceInterval) {
                                    lastClickTime = currentTime
                                    onShowDetails(podcast)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PodcastCard(
    podcast: Podcast,
    onClick: (Podcast) -> Unit
) {
    Spacer(modifier = Modifier.height(Dimensions.spacingSmall))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick (podcast) },
        elevation = CardDefaults.cardElevation(defaultElevation = Dimensions.cardElevation),
        shape = RoundedCornerShape(size = Dimensions.cornerRadiusMedium),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(Dimensions.paddingSmall)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(podcast.image), // Load image from URL
                contentDescription = "Podcast Image",
                modifier = Modifier
                    .size(Dimensions.listIconSize)
                    .clip(RoundedCornerShape(Dimensions.cornerRadiusMedium))
            )

            Spacer(modifier = Modifier.width(Dimensions.spacingLarge))

            // Title and Publisher
            Column(modifier = Modifier
                .align(Alignment.Top)
                .padding(top = Dimensions.paddingMedium)
            ) {
                Text(
                    text = podcast.title,
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(Dimensions.spacingExtraSmall))

                Text(
                    text = podcast.publisher,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PodcastListPreview() {
    val samplePodcasts = List(6) { index ->
        Podcast(
            id = index.toString(),
            title = "Podcast $index",
            description = "Description $index",
            image = "",
            publisher = "Publisher $index"
        )
    }

    PodcastsTheme {
        PodcastListUI(
            podcasts = samplePodcasts,
            onShowDetails = {}
        )
    }
}