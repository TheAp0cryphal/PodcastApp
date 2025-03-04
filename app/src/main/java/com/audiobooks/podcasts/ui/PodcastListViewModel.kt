package com.audiobooks.podcasts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.network.PodcastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state of the podcast list.
 * It fetches podcasts from the API and updates the UI state.
 */
class PodcastListViewModel : ViewModel() {
    private val repository = PodcastRepository()

    private val _podcasts = MutableStateFlow<List<Podcast>>(emptyList())
    val podcasts: StateFlow<List<Podcast>> = _podcasts.asStateFlow()

    fun fetchPodcastFromAPI() {
        // Launching a new coroutine to fetch podcasts on the IO thread to avoid blocking the main thread
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getPodcasts()

            result.fold(
                onSuccess = { fetchedPodcasts ->
                    println("Fetched podcasts: $podcasts")
                    _podcasts.value = fetchedPodcasts
                },
                onFailure = { error ->
                    println("Failed to fetch podcasts: ${error.message}")
                }
            )
        }
    }
}