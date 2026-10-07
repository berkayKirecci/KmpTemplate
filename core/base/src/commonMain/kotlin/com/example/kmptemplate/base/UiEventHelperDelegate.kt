package com.example.kmptemplate.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class UiEventHelperDelegate : UiEventHelper {

    /**
     * A buffered Channel rather than a MutableSharedFlow: a SharedFlow with no replay drops
     * anything emitted while nothing is collecting, and view models load in `init` - long
     * before the Composable subscribes - so load errors were never shown.
     */
    private val events = Channel<BaseUiEvent>(Channel.BUFFERED)

    override val uiEvent: Flow<BaseUiEvent> = events.receiveAsFlow()

    override suspend fun emitEvent(event: BaseUiEvent) {
        events.send(event)
    }
}
