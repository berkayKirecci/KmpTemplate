package com.example.kmptemplate.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class Navigator {
    private var backStack: NavBackStack<NavKey>? = null

    fun attach(backStack: NavBackStack<NavKey>) {
        this.backStack = backStack
    }

    fun navigateTo(destination: NavKey) {
        backStack?.add(destination)
    }

    fun navigateBack() {
        backStack?.removeLastOrNull()
    }

    fun navigateBackTo(destination: NavKey) {
        val index = backStack?.indexOfLast { it == destination } ?: return
        if (index >= 0) {
            backStack?.subList(index + 1, backStack!!.size)?.clear()
        }
    }
}
