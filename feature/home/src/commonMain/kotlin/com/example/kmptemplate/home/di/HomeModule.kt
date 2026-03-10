package com.example.kmptemplate.home.di

import com.example.kmptemplate.home.ui.HomeViewmodel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val homeModule = module {
    viewModel<HomeViewmodel>()
}

