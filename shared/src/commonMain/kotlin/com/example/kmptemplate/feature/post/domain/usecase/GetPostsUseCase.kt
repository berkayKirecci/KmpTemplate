package com.example.kmptemplate.feature.post.domain.usecase

import com.example.kmptemplate.feature.post.data.repository.PostRepository
import com.example.kmptemplate.feature.post.data.response.PostListResponse
import com.example.kmptemplate.network.model.ApiResponseWrapper
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(private val postRepository: PostRepository) {

    operator fun invoke(): Flow<ApiResponseWrapper<PostListResponse>> = postRepository.getPosts()
}
