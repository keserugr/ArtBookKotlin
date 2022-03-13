package com.uho.artbookkotlin.model

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHints: Int
)