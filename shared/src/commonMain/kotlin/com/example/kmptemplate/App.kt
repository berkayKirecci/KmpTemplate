package com.example.kmptemplate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kmptemplate.di.appModule
import com.example.kmptemplate.navigation.Navigation
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinConfiguration

@Composable
fun App() {
    KoinApplication(configuration = KoinConfiguration {
        modules(appModule)
    }) {
        MaterialTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Navigation(Modifier.padding(innerPadding))
            }
        }
    }
}