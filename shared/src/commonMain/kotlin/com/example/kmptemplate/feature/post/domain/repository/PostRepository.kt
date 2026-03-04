package com.example.kmptemplate.feature.post.domain.repository

import com.example.kmptemplate.feature.post.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
}

