package com.example.kmptemplate.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.kmptemplate.home.ui.HomeRoute
import com.example.kmptemplate.post.ui.PostRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val appNavigationSerializers = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(HomeRoute::class, HomeRoute.serializer())
        subclass(PostRoute::class, PostRoute.serializer())
    }
}

val appSavedStateConfig = SavedStateConfiguration {
    serializersModule = appNavigationSerializers
}