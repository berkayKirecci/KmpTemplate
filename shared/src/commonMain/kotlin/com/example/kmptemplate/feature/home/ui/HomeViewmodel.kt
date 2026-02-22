package com.example.kmptemplate.feature.home.ui

import androidx.lifecycle.ViewModel

class HomeViewmodel : ViewModel() {

    override fun onCleared() {
        println("HomeViewmodel.onCleared")
        super.onCleared()
    }
}