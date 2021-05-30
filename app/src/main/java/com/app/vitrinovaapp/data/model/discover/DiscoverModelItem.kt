package com.app.vitrinovaapp.data.model.discover

data class DiscoverModelItem(
    val categories: List<Category>,
    val collections: List<Collections>,
    val featured: List<Featured>,
    val products: List<Product>,
    val shops: List<Shop>,
    val title: String,
    val type: String
)
