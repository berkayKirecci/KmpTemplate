package com.example.kmptemplate.base

import kotlinx.coroutines.flow.SharedFlow

interface UiEventHelper {
    val uiEvent: SharedFlow<BaseUiEvent>
    fun emitEvent(event: BaseUiEvent)
}

