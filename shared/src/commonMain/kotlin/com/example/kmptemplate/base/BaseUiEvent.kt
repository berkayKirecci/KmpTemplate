package com.example.kmptemplate.base

sealed class BaseUiEvent {
    data class ShowError(val errorMessage: String) : BaseUiEvent()
}