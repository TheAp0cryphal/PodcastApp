package com.audiobooks.podcasts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme

@Composable
fun PodcastListScreen(onShowDetails: (podcast: Podcast) -> Unit) {
    // TODO - Implement the ViewModel to fetch the list of podcasts and update the UI
    // TODO - Modify this file as needed
    // TODO - Coil dependency was added as the image loader for the podcast image - feel free to use any other image loader
    val viewModel: PodcastListViewModel = viewModel()

    PodcastListUI(
        onShowDetails = onShowDetails
    )

}

@Composable
private fun PodcastListUI(
    onShowDetails: (podcast: Podcast) -> Unit
) {
    // TODO - Example UI layout - Modify to implement the requested UI
    Column {
        Text("Podcast List Screen")
        Button(
            onClick = {
                onShowDetails(
                    // Example data
                    Podcast(
                        title = "Example Podcast Title",
                        description="The Ed Mylett Show showcases the greatest peak-performers across all industries in one place",
                        id="abc",
                        image="https://cdn-images-3.listennotes.com/podcasts/the-ed-mylett-show-ed-mylett-cumulus-guxpvEVnHTJ-PEUIT9RBhZD.1400x1400.jpg",
                        publisher="Podcast Publisher"
                    )
                )
            }
        ) {
            Text("Show Details")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PodcastListUIPreview() {
    PodcastsTheme {
        PodcastListUI(
            onShowDetails = {}
        )
    }
}
