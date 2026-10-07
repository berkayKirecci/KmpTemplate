package com.example.kmptemplate.base

import kotlinx.coroutines.flow.Flow

interface UiEventHelper {
    /**
     * One-shot UI events. Each event is delivered to exactly one collector, and events
     * emitted before the UI subscribes are buffered rather than dropped.
     */
    val uiEvent: Flow<BaseUiEvent>

    suspend fun emitEvent(event: BaseUiEvent)
}
