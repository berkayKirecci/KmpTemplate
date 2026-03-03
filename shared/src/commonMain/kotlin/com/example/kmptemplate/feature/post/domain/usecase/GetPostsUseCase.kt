package com.example.kmptemplate.feature.post.domain.usecase

import com.example.kmptemplate.feature.post.data.repository.PostRepository
import com.example.kmptemplate.feature.post.data.response.PostListResponse
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(private val postRepository: PostRepository) {

    operator fun invoke(): Flow<Result<PostListResponse>> = postRepository.getPosts()
}
