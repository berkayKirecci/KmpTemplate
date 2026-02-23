package com.example.kmptemplate.feature.home.ui

import androidx.lifecycle.ViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HomeViewmodel : ViewModel() {

    override fun onCleared() {
        println("HomeViewmodel.onCleared")
        super.onCleared()
    }
}