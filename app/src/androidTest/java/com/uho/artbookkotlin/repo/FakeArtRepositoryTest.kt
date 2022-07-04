package com.uho.artbookkotlin.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uho.artbookkotlin.db.Art
import com.uho.artbookkotlin.model.ImageResponse
import com.uho.artbookkotlin.util.Resource

class FakeArtRepositoryTest: IArtRepository {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}