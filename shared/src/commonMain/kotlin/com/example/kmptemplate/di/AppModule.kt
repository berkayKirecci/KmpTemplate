package com.example.kmptemplate.di

import com.example.kmptemplate.feature.animation.ui.AnimationViewmodel
import com.example.kmptemplate.feature.home.ui.HomeViewmodel
import com.example.kmptemplate.platform.DataStore
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.viewModel

val appModule = module {
    single<DataStore>()
    viewModel<HomeViewmodel>()
    viewModel<AnimationViewmodel>()
}