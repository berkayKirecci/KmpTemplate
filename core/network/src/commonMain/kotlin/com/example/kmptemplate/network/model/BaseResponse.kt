package com.example.kmptemplate.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    @SerialName("errorMessage")
    val errorMessage: String? = null,
    @SerialName("isError")
    val isError: Boolean? = null
)

