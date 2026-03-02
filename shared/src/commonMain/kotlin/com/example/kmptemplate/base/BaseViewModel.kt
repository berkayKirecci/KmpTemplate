package com.example.kmptemplate.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptemplate.network.model.ApiResponseWrapper
import com.example.kmptemplate.network.model.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val loadingState: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val uiEvent: SharedFlow<BaseUiEvent>
        field = MutableSharedFlow()

    fun showEvent(event: BaseUiEvent) = viewModelScope.launch {
        uiEvent.emit(event)
    }

    suspend fun <T : BaseResponse> Flow<ApiResponseWrapper<T>>.safeCollect(
        showError: Boolean = true,
        showLoading: Boolean = true,
        onSuccess: T.() -> Unit
    ) {
        this.onStart { loadingState.update { showLoading } }
            .collectLatest { response ->
                loadingState.update { false }
                when (response) {
                    is ApiResponseWrapper.Success -> onSuccess(response.response)
                    is ApiResponseWrapper.Error -> if (showError) showEvent(
                        BaseUiEvent.ShowError(response.errorMessage)
                    )
                }
            }
    }
}