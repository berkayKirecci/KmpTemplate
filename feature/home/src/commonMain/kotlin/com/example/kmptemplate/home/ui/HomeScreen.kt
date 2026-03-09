package com.example.kmptemplate.home.ui

import androidx.compose.runtime.Composable
import com.example.kmptemplate.designsystem.BaseScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(viewmodel: HomeViewmodel = koinViewModel()) {
    BaseScreen(viewmodel) {

    }
}