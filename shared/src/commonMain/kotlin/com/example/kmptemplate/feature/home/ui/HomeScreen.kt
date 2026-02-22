package com.example.kmptemplate.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    onAnimationClick: () -> Unit,
    viewmodel: HomeViewmodel = viewModel()
) {
    Column {
        Button(onClick = onAnimationClick) {
            Text("Animasyonlar")
        }
    }
}