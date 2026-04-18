package com.example.kmptemplate.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class NetworkHelperDelegate(
    initialLoading: Boolean = true,
    uiEventHelper: UiEventHelper = UiEventHelperDelegate(),
) : NetworkHelper, UiEventHelper by uiEventHelper {

    private val mutableState = MutableStateFlow(initialLoading)
    override val loadingState: StateFlow<Boolean> = mutableState.asStateFlow()

    override suspend fun <T> Flow<T>.safeCollect(onSuccess: T.() -> Unit) {
        this.onStart { mutableState.value = true }
            .catch {
                mutableState.value = false
                emitEvent(BaseUiEvent.ShowError(it.message.orEmpty()))
            }
            .flowOn(Dispatchers.IO)
            .collectLatest {
                mutableState.value = false
                onSuccess(it)
            }
    }

    override suspend fun <T> safeCall(block: suspend () -> T, onSuccess: T.() -> Unit) {
        try {
            val result = withContext(Dispatchers.IO) { block() }
            mutableState.value = false
            onSuccess(result)
        } catch (e: Exception) {
            mutableState.value = false
            emitEvent(BaseUiEvent.ShowError(e.message.orEmpty()))
        }
    }
}