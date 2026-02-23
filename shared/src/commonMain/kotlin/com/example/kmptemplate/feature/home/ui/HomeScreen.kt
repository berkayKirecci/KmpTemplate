package com.example.kmptemplate.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onAnimationClick: () -> Unit,
    viewmodel: HomeViewmodel = koinViewModel()
) {
    Column {
        Button(onClick = onAnimationClick) {
            Text("Animasyonlar")
        }
    }
}