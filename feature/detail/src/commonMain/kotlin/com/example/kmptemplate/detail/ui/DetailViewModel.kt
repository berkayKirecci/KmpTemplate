package com.example.kmptemplate.detail.ui

import androidx.lifecycle.ViewModel
import com.example.kmptemplate.base.NetworkHelper
import com.example.kmptemplate.base.NetworkHelperDelegate

class DetailViewModel : ViewModel(), NetworkHelper by NetworkHelperDelegate() {

    override fun onCleared() {
        println("DetailViewModel.onCleared")
        super.onCleared()
    }
}