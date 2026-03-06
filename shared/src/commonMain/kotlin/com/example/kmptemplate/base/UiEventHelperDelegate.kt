package com.example.kmptemplate.base

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class UiEventHelperDelegate : UiEventHelper {

    final override val uiEvent: SharedFlow<BaseUiEvent>
        field = MutableSharedFlow()

    override fun emitEvent(event: BaseUiEvent) {
        uiEvent.tryEmit(event)
    }
}

