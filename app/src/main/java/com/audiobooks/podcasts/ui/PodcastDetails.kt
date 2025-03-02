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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.favouriteButtonColor

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
                .padding(horizontal = 5.dp),
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
                stringResource(R.string.Back),
                color = Color.Black,
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
        var isFavourited by remember { mutableStateOf(false) }
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
                text = if (isFavourited) stringResource(R.string.Favourited) else stringResource(R.string.Favourite),
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

private fun stripHtml(html: String): String {
    return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
}
