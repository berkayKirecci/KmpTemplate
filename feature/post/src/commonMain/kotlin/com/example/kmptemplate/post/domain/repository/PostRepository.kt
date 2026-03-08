package com.example.kmptemplate.post.domain.repository

import com.example.kmptemplate.post.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
}

