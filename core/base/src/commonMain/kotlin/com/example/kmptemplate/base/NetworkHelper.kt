package com.example.kmptemplate.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NetworkHelper : UiEventHelper {
    val loadingState: StateFlow<Boolean>
    suspend fun <T> Flow<T>.safeCollect(onSuccess: T.() -> Unit)
    suspend fun <T> safeCall(block: suspend () -> T, onSuccess: T.() -> Unit)
}
