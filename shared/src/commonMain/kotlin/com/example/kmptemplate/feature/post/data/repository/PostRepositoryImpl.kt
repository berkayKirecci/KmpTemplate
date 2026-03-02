package com.example.kmptemplate.feature.post.data.repository

import com.example.kmptemplate.feature.post.data.response.PostListResponse
import com.example.kmptemplate.network.NetworkClient
import com.example.kmptemplate.network.model.ApiResponseWrapper
import com.example.kmptemplate.network.safeFlowRequest
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(private val networkClient: NetworkClient) : PostRepository {

    override fun getPosts(): Flow<ApiResponseWrapper<PostListResponse>> = safeFlowRequest {
        networkClient.get("posts")
    }
}
