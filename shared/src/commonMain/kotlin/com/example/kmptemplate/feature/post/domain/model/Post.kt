package com.example.kmptemplate.feature.post.domain.model

/**
 * Domain model for a Post.
 * Decouples the UI and use-case layer from the raw API response DTOs.
 */
data class Post(
    val id: Int,
    val title: String,
    val body: String,
)

