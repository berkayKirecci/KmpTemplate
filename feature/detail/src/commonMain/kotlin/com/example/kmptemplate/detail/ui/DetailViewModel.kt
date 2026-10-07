package com.example.kmptemplate.detail.ui

import androidx.lifecycle.ViewModel
import com.example.kmptemplate.base.Log
import com.example.kmptemplate.base.NetworkHelper
import com.example.kmptemplate.base.NetworkHelperDelegate

class DetailViewModel : ViewModel(), NetworkHelper by NetworkHelperDelegate() {

    override fun onCleared() {
        Log.d("DetailViewModel", "onCleared")
        super.onCleared()
    }
}