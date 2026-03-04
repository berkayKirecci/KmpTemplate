package com.example.kmptemplate.feature.post.data.response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class PostResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("body") val body: String? = null,
)
