package com.audiobooks.podcasts.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme

@Composable
fun PodcastDetailsScreen(podcast: Podcast) {
    // TODO Implement the requested UI
    Text("Podcast Details Screen")
}

@Preview(showBackground = true)
@Composable
private fun PodcastDetailsScreenPreview() {
    PodcastsTheme {
        PodcastDetailsScreen(
            podcast = Podcast(
                title = "Example Podcast Title",
                description="The Ed Mylett Show showcases the greatest peak-performers across all industries in one place",
                id="abc",
                image="https://cdn-images-3.listennotes.com/podcasts/the-ed-mylett-show-ed-mylett-cumulus-guxpvEVnHTJ-PEUIT9RBhZD.1400x1400.jpg",
                publisher="Podcast Publisher"
            )
        )
    }
}
