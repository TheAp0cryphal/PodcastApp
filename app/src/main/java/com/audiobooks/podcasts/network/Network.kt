package com.audiobooks.podcasts.network

import com.audiobooks.podcasts.model.BestPodcastResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiService {
    private const val BASE_URL = "https://listen-api-test.listennotes.com/api/v2/"

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service : PodcastApi = getRetrofit().create(PodcastApi::class.java)
}

interface PodcastApi {
    @GET("best_podcasts")
    suspend fun getBestPodcasts(): BestPodcastResponse
}