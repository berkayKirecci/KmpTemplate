package com.example.kmptemplate.post.domain.usecase

import com.example.kmptemplate.post.domain.repository.PostRepository

class GetPostsUseCase(private val postRepository: PostRepository) {

    operator fun invoke() = postRepository.getPosts()
}
