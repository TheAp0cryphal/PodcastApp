package com.audiobooks.podcasts.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.audiobooks.podcasts.model.Podcast
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
object PodcastList
@Serializable
data class PodcastDetails(
    val podcast: Podcast
)

object CustomNavType {
    val PodcastType = object : NavType<Podcast>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Podcast {
            return Json.decodeFromString(bundle.getString(key) ?: throw IllegalArgumentException("Podcast not found in bundle"))
        }

        override fun parseValue(value: String): Podcast {
            return Json.decodeFromString(value)
        }

        override fun serializeAsValue(value: Podcast): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Podcast) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}
