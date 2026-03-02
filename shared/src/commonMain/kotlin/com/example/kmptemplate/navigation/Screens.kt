package com.example.kmptemplate.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Screens : NavKey {

    @Serializable
    data object Home : Screens()

    @Serializable
    data object Post : Screens()
}