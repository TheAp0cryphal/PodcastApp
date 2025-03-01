package com.audiobooks.podcasts.model

import kotlinx.serialization.Serializable

@Serializable
data class Podcast(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val publisher: String
)
