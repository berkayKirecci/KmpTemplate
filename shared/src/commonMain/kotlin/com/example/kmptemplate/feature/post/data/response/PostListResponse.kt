package com.example.kmptemplate.feature.post.data.response

import com.example.kmptemplate.network.model.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostListResponse(
    @SerialName("posts")
    val posts: List<PostResponse>? = null
) : BaseResponse()