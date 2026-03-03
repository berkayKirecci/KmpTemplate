package com.example.kmptemplate.base

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
import state.rememberSnackbarState
import ui.MultiPlatformSnackbar

@Composable
fun BaseScreen(
    viewModel: BaseViewModel,
    content: @Composable ColumnScope.() -> Unit
) {
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarState = rememberSnackbarState()

    LaunchedEffect(viewModel.uiEvent, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is BaseUiEvent.ShowError -> snackbarState.error(event.errorMessage)
                }
            }
        }
    }

    if (loadingState) Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
    MultiPlatformSnackbar(state = snackbarState)

    Column(modifier = Modifier.fillMaxSize()) {
        content()
    }
}
