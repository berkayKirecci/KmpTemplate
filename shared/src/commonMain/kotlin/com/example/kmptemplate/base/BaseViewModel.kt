package com.example.kmptemplate.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptemplate.network.model.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val loadingState: StateFlow<Boolean>
        field = MutableStateFlow(false)

    val uiEvent: SharedFlow<BaseUiEvent>
        field = MutableSharedFlow()

    fun emitEvent(event: BaseUiEvent) = viewModelScope.launch {
        uiEvent.emit(event)
    }

    suspend fun <T : BaseResponse> Flow<T>.safeCollect(onSuccess: T.() -> Unit) {
        this.onStart { loadingState.value = true }
            .catch {
                loadingState.value = false
                emitEvent(BaseUiEvent.ShowError(it.message.orEmpty()))
            }
            .collectLatest {
                loadingState.value = false
                onSuccess(it)
            }
    }
}