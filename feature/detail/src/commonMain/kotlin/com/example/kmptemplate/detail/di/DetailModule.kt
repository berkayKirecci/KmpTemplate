package com.example.kmptemplate.detail.di

import com.example.kmptemplate.detail.ui.DetailScreen
import com.example.kmptemplate.detail.ui.DetailViewModel
import com.example.kmptemplate.navigation.DetailRoute
import com.example.kmptemplate.navigation.navKeyModule
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.viewModel

@OptIn(KoinExperimentalAPI::class)
val detailModule = module {
    includes(navKeyModule(DetailRoute.serializer()))
    viewModel<DetailViewModel>()
    navigation<DetailRoute> {
        DetailScreen(viewmodel = koinViewModel())
    }
}
