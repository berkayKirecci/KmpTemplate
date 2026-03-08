package com.example.kmptemplate.post.data.repository

import com.example.kmptemplate.network.NetworkClient
import com.example.kmptemplate.post.data.response.PostListResponse
import com.example.kmptemplate.post.data.response.toDomain
import com.example.kmptemplate.post.domain.repository.PostRepository
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(private val networkClient: NetworkClient) : PostRepository {

    override fun getPosts() = networkClient.get<PostListResponse>("posts").map {
        it.toDomain()
    }
}
