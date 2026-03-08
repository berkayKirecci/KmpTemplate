package com.example.kmptemplate.post.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("body") val body: String? = null,
)
