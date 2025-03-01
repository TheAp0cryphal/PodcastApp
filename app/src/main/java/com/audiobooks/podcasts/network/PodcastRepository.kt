package com.audiobooks.podcasts.network

import com.audiobooks.podcasts.model.Podcast

class PodcastRepository(
    private val service: PodcastApi = ApiService.service
) {
    // Modify if needed
    suspend fun getPodcasts(): Result<List<Podcast>> {
        return try {
            Result.success(service.getBestPodcasts().podcasts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
