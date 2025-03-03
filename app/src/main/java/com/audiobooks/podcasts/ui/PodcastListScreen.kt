package com.audiobooks.podcasts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

const val debounceInterval = 1000L // Debounce interval in milliseconds, I chose this number because it prevent click during fade animation

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
    var lastClickTime by remember { mutableLongStateOf(0L) }

    // Column is a layout composable that places its children in a vertical sequence
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        // "Podcasts"
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = stringResource(R.string.podcasts),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
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
                    contentPadding = PaddingValues(vertical = 16.dp)
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
    Spacer(modifier = Modifier.height(4.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick (podcast) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(podcast.image), // Load image from URL
                contentDescription = "Podcast Image",
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier
                .align(Alignment.Top)
                .padding(top = 12.dp)
            ) {
                Text(
                    text = podcast.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.01.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = podcast.publisher,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
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