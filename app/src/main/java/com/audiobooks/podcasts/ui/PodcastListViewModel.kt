package com.audiobooks.podcasts.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.network.PodcastRepository
import kotlinx.coroutines.launch

class PodcastListViewModel : ViewModel() {
    private val repository = PodcastRepository()

    var podcasts by mutableStateOf<List<Podcast>>(emptyList())
        private set

    fun fetchPodcastFromAPI() {
        viewModelScope.launch {
            val result = repository.getPodcasts()

            result.fold(
                onSuccess = { fetchedPodcasts ->
                    println("Fetched podcasts: $podcasts")
                    podcasts = fetchedPodcasts
                },
                onFailure = { error ->
                    println("Failed to fetch podcasts: ${error.message}")
                }
            )

        }
    }
}
