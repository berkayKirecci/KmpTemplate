package com.example.kmptemplate.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart

class NetworkHelperDelegate(
    uiEventHelper: UiEventHelper = UiEventHelperDelegate(),
) : NetworkHelper, UiEventHelper by uiEventHelper {

    private val mutableState = MutableStateFlow(false)
    override val loadingState: StateFlow<Boolean> = mutableState.asStateFlow()

    override suspend fun <T> Flow<T>.safeCollect(onSuccess: T.() -> Unit) {
        this.onStart { mutableState.value = true }
            .catch {
                mutableState.value = false
                emitEvent(BaseUiEvent.ShowError(it.message.orEmpty()))
            }
            .collectLatest {
                mutableState.value = false
                onSuccess(it)
            }
    }
}