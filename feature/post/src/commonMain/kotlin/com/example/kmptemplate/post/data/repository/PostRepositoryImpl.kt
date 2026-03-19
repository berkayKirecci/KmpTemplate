package com.example.kmptemplate.post.data.repository

import com.example.kmptemplate.network.NetworkClient
import com.example.kmptemplate.post.data.response.PostListResponse
import com.example.kmptemplate.post.data.response.toDomain
import com.example.kmptemplate.post.domain.repository.PostRepository

class PostRepositoryImpl(private val networkClient: NetworkClient) : PostRepository {

    override suspend fun getPosts() = networkClient.request<PostListResponse>("posts").toDomain()
}
