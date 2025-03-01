package com.audiobooks.podcasts.ui

import androidx.lifecycle.ViewModel
import com.audiobooks.podcasts.network.PodcastRepository

class PodcastListViewModel : ViewModel() {
    private val repository = PodcastRepository()
    // TODO - Make the API call using repository.getPodcasts() and update the UI
}
