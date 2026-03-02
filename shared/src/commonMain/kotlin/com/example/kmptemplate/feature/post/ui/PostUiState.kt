package com.example.kmptemplate.feature.post.ui

import com.example.kmptemplate.feature.post.data.response.PostResponse

data class PostUiState(
    val posts: List<PostResponse> = emptyList()
)
