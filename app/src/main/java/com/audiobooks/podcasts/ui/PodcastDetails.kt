package com.audiobooks.podcasts.ui

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme
import com.audiobooks.podcasts.ui.theme.favouriteButtonColor

/**
 * Composable function to display the details of a podcast.
 *
 * @param podcast The podcast object containing details to be displayed.
 * @param onBack Callback function to handle the back navigation.
 */

@Composable
fun PodcastDetailsScreen(
    podcast: Podcast,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState() // Added scroll state for text descriptions longer than the screen
    var backClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        //Back Button
        Button(
            onClick = {
                if (!backClicked) {
                    // Prevent multiple clicks on the back button which may cause popping on the backstack multiple times
                    backClicked = true
                    onBack() // Callback to navigate back provided by MainActivity
                }
            },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp),
            contentPadding = PaddingValues(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(20.dp)
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                stringResource(R.string.back),
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 24.dp)
                .padding(horizontal = 26.dp), // Padding from left and right to prevent text from sticking to the edges
            text = podcast.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        //Publisher
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            text = podcast.publisher,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(22.dp))

        //Image
        Image(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(250.dp)
                .clip(RoundedCornerShape(16.dp)),
            painter = rememberAsyncImagePainter(podcast.image),
            contentDescription = "Podcast Image",
        )

        //Favourite Button
        var isFavourited by rememberSaveable { mutableStateOf(false) }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 26.dp),
            contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
            onClick = { isFavourited = !isFavourited },
            colors = ButtonDefaults.buttonColors(containerColor = favouriteButtonColor),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(
                text = if (isFavourited) stringResource(R.string.favourited) else stringResource(R.string.favourite),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        //Description
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(32.dp),
            text = stripHtml(podcast.description),
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
        )
    }
}

/* Helper function: Strips HTML tags from the description */
private fun stripHtml(html: String): String {
    return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
}


@Preview(showBackground = true)
@Composable
fun PodcastDetailsPreview() {
    val samplePodcast = Podcast(
        id = "1",
        title = "Sample Podcast",
        description = "This is a sample podcast description.",
        image = "",
        publisher = "Sample Publisher"
    )

    PodcastsTheme {
        PodcastDetailsScreen(
            podcast = samplePodcast,
            onBack = {}
        )
    }
}
