package com.example.kmptemplate.feature.post.data.repository

import com.example.kmptemplate.feature.post.data.response.PostListResponse
import com.example.kmptemplate.feature.post.data.response.toDomain
import com.example.kmptemplate.feature.post.domain.repository.PostRepository
import com.example.kmptemplate.network.NetworkClient
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(private val networkClient: NetworkClient) : PostRepository {

    override fun getPosts() = networkClient.get<PostListResponse>("posts").map {
        it.toDomain()
    }
}
