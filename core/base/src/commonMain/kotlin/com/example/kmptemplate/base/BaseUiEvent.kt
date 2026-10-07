package com.example.kmptemplate.base

sealed class BaseUiEvent {
    data class ShowError(
        val error: AppError,
        val serverMessage: String? = null,
    ) : BaseUiEvent()
}
