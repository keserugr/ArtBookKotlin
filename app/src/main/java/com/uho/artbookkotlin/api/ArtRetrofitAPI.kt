package com.uho.artbookkotlin.api

import com.uho.artbookkotlin.model.ImageResponse
import com.uho.artbookkotlin.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtRetrofitAPI {

    @GET("api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ): Response<ImageResponse>
}