package com.app.vitrinovaapp.data.model.discover

data class Cover(
    val height: Int,
    val medium: CoverMedium,
    val thumbnail: CoverThumbnail,
    val url: String,
    val width: Int
)