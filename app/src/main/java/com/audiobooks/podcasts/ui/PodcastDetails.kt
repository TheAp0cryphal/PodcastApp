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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 24.dp),
            text = podcast.title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
        )

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            text = podcast.publisher,
            color = Color.Gray,
            fontStyle = FontStyle.Italic
        )

        Image(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 32.dp)
                .size(250.dp)
                .clip(RoundedCornerShape(16.dp)),
            painter = rememberAsyncImagePainter(podcast.image),
            contentDescription = "Podcast Image",
        )


        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 26.dp),
            contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
            onClick = { /* TODO */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0050)), // Custom Pink/Red
            shape = RoundedCornerShape(12.dp), // Rounded corners
        ) {
            Text(
                text = stringResource(R.string.Favourite),
                fontSize = 16.sp,
                color = Color.White, // White text
            )
        }

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(32.dp),
            text = stripHtml(podcast.description),
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            fontSize = 12.sp,
            color = Color.Gray
        )

    }
}

fun stripHtml(html: String): String {
    return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
}
