package com.example.kmptemplate.feature.animation.ui

import androidx.lifecycle.ViewModel

class AnimationViewmodel : ViewModel() {

    override fun onCleared() {
        println("AnimationViewmodel.onCleared")
        super.onCleared()
    }
}