package com.example.kmptemplate.feature.post.ui

import com.example.kmptemplate.feature.post.domain.model.Post
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PostUiState(
    val posts: ImmutableList<Post> = persistentListOf(),
)
