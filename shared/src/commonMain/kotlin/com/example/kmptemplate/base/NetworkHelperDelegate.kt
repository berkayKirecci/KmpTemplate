package com.example.kmptemplate.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart

class NetworkHelperDelegate(
    uiEventHelper: UiEventHelper = UiEventHelperDelegate(),
) : NetworkHelper, UiEventHelper by uiEventHelper {

    final override val loadingState: StateFlow<Boolean>
        field = MutableStateFlow(false)

    override suspend fun <T> Flow<T>.safeCollect(onSuccess: T.() -> Unit) {
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