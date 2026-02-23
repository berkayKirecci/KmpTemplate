package com.example.kmptemplate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmptemplate.di.KoinApplication
import com.example.kmptemplate.navigation.Navigation
import org.koin.compose.KoinApplication
import org.koin.plugin.module.dsl.koinConfiguration

@Composable
@Preview
fun App() {
    KoinApplication(configuration = koinConfiguration<KoinApplication>()) {
        MaterialTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Navigation(Modifier.padding(innerPadding))
            }
        }
    }
}