package com.uho.artbookkotlin.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uho.artbookkotlin.R

object Util{
    const val API_KEY = "25944311-d6f9011245d0aba0c93d23004"
    const val BASE_URL = "https://pixabay.com/"
}

fun ImageView.downloadFromUrl(url: String){
    val options = RequestOptions()
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String){
    url?.let {
        view.downloadFromUrl(url)
    }
}