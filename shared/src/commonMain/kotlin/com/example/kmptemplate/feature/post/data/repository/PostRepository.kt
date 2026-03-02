package com.example.kmptemplate.feature.post.data.repository

import com.example.kmptemplate.feature.post.data.response.PostListResponse
import com.example.kmptemplate.network.model.ApiResponseWrapper
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<ApiResponseWrapper<PostListResponse>>
}
