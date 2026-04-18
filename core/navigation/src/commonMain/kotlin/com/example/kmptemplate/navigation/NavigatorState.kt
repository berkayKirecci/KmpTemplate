package com.example.kmptemplate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import org.koin.compose.getKoin

internal data class NavigatorState(
    val navigator: Navigator,
    val backStack: NavBackStack<NavKey>,
)

@Composable
internal fun rememberNavigator(startDestination: NavKey = PostRoute): NavigatorState {
    val koin = getKoin()
    val navigator = remember { koin.get<Navigator>() }

    val serializers = remember { koin.getAll<NavRouteSerializer>() }
    val combinedSerializers = remember(serializers) {
        SerializersModule { serializers.forEach { include(it.serializersModule) } }
    }

    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = combinedSerializers
        }, startDestination
    )

    navigator.attach(backStack)
    return NavigatorState(navigator, backStack)
}

