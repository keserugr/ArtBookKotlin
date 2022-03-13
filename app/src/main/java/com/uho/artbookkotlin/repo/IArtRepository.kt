package com.uho.artbookkotlin.repo

import androidx.lifecycle.LiveData
import com.uho.artbookkotlin.db.Art
import com.uho.artbookkotlin.model.ImageResponse
import com.uho.artbookkotlin.util.Resource

interface IArtRepository {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>

}