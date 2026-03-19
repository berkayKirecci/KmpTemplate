package com.example.kmptemplate.post.domain.repository

import com.example.kmptemplate.post.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
}