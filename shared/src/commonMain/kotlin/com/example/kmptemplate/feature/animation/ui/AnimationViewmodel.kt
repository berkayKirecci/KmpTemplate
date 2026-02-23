package com.example.kmptemplate.feature.animation.ui

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class AnimationViewmodel : ViewModel() {

    override fun onCleared() {
        println("AnimationViewmodel.onCleared")
        super.onCleared()
    }
}