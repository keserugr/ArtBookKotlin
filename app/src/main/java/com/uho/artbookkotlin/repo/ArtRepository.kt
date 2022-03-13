package com.uho.artbookkotlin.repo

import androidx.lifecycle.LiveData
import com.uho.artbookkotlin.api.ArtRetrofitAPI
import com.uho.artbookkotlin.db.Art
import com.uho.artbookkotlin.db.ArtDao
import com.uho.artbookkotlin.model.ImageResponse
import com.uho.artbookkotlin.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: ArtRetrofitAPI
) : IArtRepository {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.delete(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }
}