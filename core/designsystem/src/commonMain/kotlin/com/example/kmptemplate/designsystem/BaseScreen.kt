package com.example.kmptemplate.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.kmptemplate.base.BaseUiEvent
import com.example.kmptemplate.base.NetworkHelper
import com.example.kmptemplate.base.UiEventHelper
import state.rememberSnackbarState
import ui.MultiPlatformSnackbar

@Composable
fun BaseScreen(
    networkHelper: NetworkHelper,
    content: @Composable ColumnScope.() -> Unit
) {
    val loadingState by networkHelper.loadingState.collectAsStateWithLifecycle()

    BaseScreen(uiEventHelper = networkHelper) {
        if (loadingState) Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        content()
    }
}

@Composable
fun BaseScreen(
    uiEventHelper: UiEventHelper,
    content: @Composable ColumnScope.() -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarState = rememberSnackbarState()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEventHelper.uiEvent.collect { event ->
                when (event) {
                    is BaseUiEvent.ShowError -> snackbarState.error(event.errorMessage)
                }
            }
        }
    }

    MultiPlatformSnackbar(state = snackbarState)

    Column(modifier = Modifier.fillMaxSize()) {
        content()
    }
}
