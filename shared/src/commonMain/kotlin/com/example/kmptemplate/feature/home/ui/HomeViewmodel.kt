package com.example.kmptemplate.feature.home.ui

import androidx.lifecycle.ViewModel
import com.example.kmptemplate.platform.DataStore

class HomeViewmodel(private val storage: DataStore) : ViewModel() {

    override fun onCleared() {
        println("HomeViewmodel.onCleared")
        super.onCleared()
    }
}