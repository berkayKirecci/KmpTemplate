package com.example.kmptemplate.network.model

sealed class ApiResponseWrapper<out T : BaseResponse> {
    data class Success<T : BaseResponse>(val response: T) : ApiResponseWrapper<T>()
    data class Error(val errorMessage: String) : ApiResponseWrapper<Nothing>()
}
