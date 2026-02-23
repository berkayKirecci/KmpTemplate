package com.example.kmptemplate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmptemplate.di.InitKoinApplication
import com.example.kmptemplate.navigation.Navigation
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    InitKoinApplication(koinAppDeclaration) {
        MaterialTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Navigation(Modifier.padding(innerPadding))
            }
        }
    }
}