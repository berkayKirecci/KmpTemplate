package com.example.kmptemplate.feature.post.domain.usecase

import com.example.kmptemplate.feature.post.domain.repository.PostRepository

class GetPostsUseCase(private val postRepository: PostRepository) {

    operator fun invoke() = postRepository.getPosts()
}
