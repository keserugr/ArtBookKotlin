package com.uho.artbookkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uho.artbookkotlin.db.Art
import com.uho.artbookkotlin.model.ImageResponse
import com.uho.artbookkotlin.repo.IArtRepository
import com.uho.artbookkotlin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: IArtRepository
) : ViewModel() {
    //Art Fragment
    val artList = repository.getArt()

    //Image API Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageURL: LiveData<String>
        get() = selectedImage

    //Art Details Fragment
    private var insertArtMessage = MutableLiveData<Resource<Art>>()
    val insertedArtMessage: LiveData<Resource<Art>>
        get() = insertArtMessage

    fun resetInsertArtMessage(){
        insertArtMessage = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch{
        repository.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String){
        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMessage.postValue(Resource.error("Enter a name, artist, and year!",null))
            return
        }
        val yearInt = try {
            year.toInt()
        }catch (e: Exception){
            insertArtMessage.postValue(Resource.error("Year has to be a number", null))
            return
        }
        val art = Art(name, artistName, yearInt, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMessage.postValue(Resource.success(art))
    }

    fun searchForImage(searchString: String){
        if (searchString.isEmpty())
            return
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response =  repository.searchImage(searchString)
            images.value = response
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}