package com.example.kmptemplate.feature.post.data.repository

import com.example.kmptemplate.feature.post.data.response.PostListResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<PostListResponse>
}
