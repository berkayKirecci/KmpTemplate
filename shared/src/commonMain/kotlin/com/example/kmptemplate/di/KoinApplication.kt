package com.example.kmptemplate.di

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinConfiguration

@Composable
fun InitKoinApplication(
    koinAppDeclaration: KoinAppDeclaration?,
    content: @Composable (() -> Unit)
) {
    KoinApplication(
        configuration = koinConfiguration(declaration = {
            koinAppDeclaration?.invoke(this)
            modules()
        }),
        content = content
    )
}