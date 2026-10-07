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
import com.example.kmptemplate.base.AppError
import com.example.kmptemplate.base.BaseUiEvent
import com.example.kmptemplate.base.NetworkHelper
import com.example.kmptemplate.base.UiEventHelper
import com.example.kmptemplate.designsystem.resources.Res
import com.example.kmptemplate.designsystem.resources.error_connection
import com.example.kmptemplate.designsystem.resources.error_server
import com.example.kmptemplate.designsystem.resources.error_unknown
import io.github.berkaykirecci.crossmessages.snackbar.CrossSnackbarHost
import io.github.berkaykirecci.crossmessages.snackbar.rememberCrossSnackbarHostState
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

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
    val snackbarState = rememberCrossSnackbarHostState()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEventHelper.uiEvent.collect { event ->
                when (event) {
                    is BaseUiEvent.ShowError ->
                        // Prefer the server's own wording when it sent one; otherwise
                        // resolve the category to a localized string.
                        snackbarState.error(
                            event.serverMessage ?: getString(event.error.stringResource)
                        )
                }
            }
        }
    }

    CrossSnackbarHost(hostState = snackbarState)

    Column(modifier = Modifier.fillMaxSize()) {
        content()
    }
}

private val AppError.stringResource: StringResource
    get() = when (this) {
        AppError.CONNECTION -> Res.string.error_connection
        AppError.SERVER -> Res.string.error_server
        AppError.UNKNOWN -> Res.string.error_unknown
    }
