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
                emitEvent(it.toErrorEvent())
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
            emitEvent(e.toErrorEvent())
        }
    }
}

/**
 * core:base cannot see core:network, so the category travels on the [AppErrorAware] marker
 * rather than being matched on a concrete exception type.
 */
private fun Throwable.toErrorEvent(): BaseUiEvent.ShowError {
    val aware = this as? AppErrorAware
    return BaseUiEvent.ShowError(
        error = aware?.appError ?: AppError.UNKNOWN,
        serverMessage = aware?.serverMessage,
    )
}
