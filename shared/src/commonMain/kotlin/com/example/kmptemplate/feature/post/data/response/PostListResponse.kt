package com.example.kmptemplate.feature.post.data.response

import com.example.kmptemplate.feature.post.domain.model.Post
import com.example.kmptemplate.network.model.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostListResponse(
    @SerialName("posts")
    val posts: List<PostResponse>? = null
) : BaseResponse()

fun PostResponse.toDomain() = Post(
    id = id ?: 0,
    title = title.orEmpty(),
    body = body.orEmpty(),
)

fun PostListResponse.toDomain(): List<Post> = posts?.map {
    it.toDomain()
}.orEmpty()