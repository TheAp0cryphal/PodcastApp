package com.audiobooks.podcasts.ui

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.Dimensions
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
            .verticalScrollbar(scrollState)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(Dimensions.spacingMedium))

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
                .heightIn(Dimensions.touchTargetSize)
                .padding(horizontal = Dimensions.paddingExtraSmall),
            contentPadding = PaddingValues(horizontal = Dimensions.paddingMedium),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.back),
                tint = Color.Black,
                modifier = Modifier
                    .size(Dimensions.backIconSize)
            )

            Spacer(modifier = Modifier.size(Dimensions.spacingSmall))

            Text(
                stringResource(R.string.back),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.spacingExtraLarge))

        //Title
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(horizontal = Dimensions.paddingExtraLarge), // Padding from left and right to prevent text from sticking to the edges
            text = podcast.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(Dimensions.spacingSmall))

        //Publisher
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            text = podcast.publisher,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(Dimensions.spacingExtraLarge))

        //Image
        Image(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(Dimensions.podcastDetailsIconSize)
                .clip(RoundedCornerShape(Dimensions.cornerRadiusExtraLarge)),
            painter = rememberAsyncImagePainter(podcast.image),
            contentDescription = "Podcast Image",
        )

        Spacer(modifier = Modifier.height(Dimensions.spacingExtraLarge))

        //Favourite Button
        var isFavourited by rememberSaveable { mutableStateOf(false) }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(
                horizontal = Dimensions.favoriteButtonHorizontalPadding,
                vertical = Dimensions.favoriteButtonVerticalPadding
            ),
            onClick = { isFavourited = !isFavourited },
            colors = ButtonDefaults.buttonColors(containerColor = favouriteButtonColor),
            shape = RoundedCornerShape(Dimensions.cornerRadiusLarge),
        ) {
            Text(
                text = if (isFavourited) stringResource(R.string.favourited) else stringResource(R.string.favourite),
                style = MaterialTheme.typography.titleMedium
            )
        }

        //Description
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(Dimensions.paddingXXLarge),
            text = stripHtml(podcast.description),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

/* Helper function: Strips HTML tags from the description */
private fun stripHtml(html: String): String {
    return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
}

/*
    * Custom modifier to add a vertical scrollbar to the screen.
    * Credits to: https://stackoverflow.com/a/78453760
*/
@Composable
fun Modifier.verticalScrollbar(state: ScrollState, scrollbarWidth: Dp = 6.dp, color: Color = Color.  Gray): Modifier {
    return this.then(Modifier.drawWithContent {
        drawContent()

        val viewHeight = state.viewportSize.toFloat()
        val contentHeight = state.maxValue + viewHeight

        // Calculate the height of the scrollbar based on the ratio of visible to total content height
        val scrollBarHeight =
            (viewHeight * (viewHeight / contentHeight)).coerceIn(10.dp.toPx(), viewHeight)
        val scrollBarYoffset =
            (state.value.toFloat() / state.maxValue) * (viewHeight - scrollBarHeight)

        drawRoundRect(
            color = color,
            topLeft = Offset(size.width - scrollbarWidth.toPx(), scrollBarYoffset),
            size = Size(scrollbarWidth.toPx(), scrollBarHeight),
            cornerRadius = CornerRadius(scrollbarWidth.toPx() / 2, scrollbarWidth.toPx() / 2),
            alpha = 1f //Always visible
        )
    })
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
