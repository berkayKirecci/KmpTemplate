package com.example.kmptemplate.home.ui

import androidx.lifecycle.ViewModel
import com.example.kmptemplate.base.NetworkHelper
import com.example.kmptemplate.base.NetworkHelperDelegate

class HomeViewmodel : ViewModel(), NetworkHelper by NetworkHelperDelegate() {

    override fun onCleared() {
        println("HomeViewmodel.onCleared")
        super.onCleared()
    }
}