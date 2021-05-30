package com.app.vitrinovaapp.data.model.discover

data class ParentCategory(
    val id: Int,
    val name: String,
    val order: Int,
    val parent_category: ParentCategory?,
    val parent_id: Int?
)