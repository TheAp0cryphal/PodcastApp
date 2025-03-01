package com.audiobooks.podcasts.ui

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast

@Composable
fun PodcastDetailsScreen(
    podcast: Podcast,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        //Back Button
        Button(
            onClick = { onBack() }, // Callback to navigate back provided by MainActivity
            modifier = Modifier.align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                stringResource(R.string.Back),
                fontWeight = FontWeight.Bold
            )
        }

        //Title
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 24.dp)
                .padding(horizontal = 32.dp),
            text = podcast.title,
            style = MaterialTheme.typography.headlineMedium,
        )

        //Publisher
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            text = podcast.publisher,
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall
        )

        //Image
        Image(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 32.dp)
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
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.favourite_button_color)),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(
                text = if (isFavourited) stringResource(R.string.Favourited) else stringResource(R.string.Favourite),
                style = MaterialTheme.typography.titleMedium,
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
            color = Color.Gray,
            fontSize = 12.sp,
            lineHeight = 12.sp,
        )
    }
}

private fun stripHtml(html: String): String {
    return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
}
