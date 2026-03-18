package com.example.kmptemplate.home.di

import com.example.kmptemplate.home.ui.HomeRoute
import com.example.kmptemplate.home.ui.HomeScreen
import com.example.kmptemplate.home.ui.HomeViewmodel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.viewModel

@OptIn(KoinExperimentalAPI::class)
val homeModule = module {
    viewModel<HomeViewmodel>()
    navigation<HomeRoute> {
        HomeScreen(koinViewModel())
    }
}

