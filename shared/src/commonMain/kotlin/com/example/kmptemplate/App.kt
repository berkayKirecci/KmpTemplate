package com.example.kmptemplate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.kmptemplate.ads.AdManager
import com.example.kmptemplate.designsystem.theme.TemplateTheme
import com.example.kmptemplate.di.appModule
import com.example.kmptemplate.navigation.Navigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.dsl.KoinConfiguration

@Composable
fun App() {
    KoinApplication(configuration = KoinConfiguration {
        modules(appModule)
    }) {
        val adManager = getKoin().get<AdManager>()
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                adManager.initAds()
            }
        }
        TemplateTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Navigation(Modifier.padding(innerPadding))
            }
        }
    }
}