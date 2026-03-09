package com.example.kmptemplate.base

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class UiEventHelperDelegate : UiEventHelper {

    private val mutableEvent = MutableSharedFlow<BaseUiEvent>()
    override val uiEvent: SharedFlow<BaseUiEvent> = mutableEvent.asSharedFlow()

    override suspend fun emitEvent(event: BaseUiEvent) {
        mutableEvent.emit(event)
    }
}

