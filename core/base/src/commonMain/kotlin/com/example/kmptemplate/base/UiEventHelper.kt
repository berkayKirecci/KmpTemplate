package com.example.kmptemplate.base

import kotlinx.coroutines.flow.SharedFlow

interface UiEventHelper {
    val uiEvent: SharedFlow<BaseUiEvent>
    suspend fun emitEvent(event: BaseUiEvent)
}

