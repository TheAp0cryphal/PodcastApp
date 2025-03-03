package com.audiobooks.podcasts.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.network.PodcastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state of the podcast list.
 * It fetches podcasts from the API and updates the UI state.
 */
class PodcastListViewModel : ViewModel() {
    private val repository = PodcastRepository()

    var podcasts by mutableStateOf<List<Podcast>>(emptyList())
        private set

    fun fetchPodcastFromAPI() {
        // Launching a new coroutine to fetch podcasts on the IO thread to avoid blocking the main thread
        viewModelScope.launch(Dispatchers.IO) {
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