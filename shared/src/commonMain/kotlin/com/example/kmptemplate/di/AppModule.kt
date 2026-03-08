package com.example.kmptemplate.di

import com.example.kmptemplate.feature.home.ui.HomeViewmodel
import com.example.kmptemplate.network.createNetworkClient
import com.example.kmptemplate.platform.DataStore
import com.example.kmptemplate.post.data.repository.PostRepositoryImpl
import com.example.kmptemplate.post.domain.repository.PostRepository
import com.example.kmptemplate.post.domain.usecase.GetPostsUseCase
import com.example.kmptemplate.post.ui.PostViewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.viewModel


val networkModule = module {
    single { createNetworkClient() }
}

val appModule = module {
    includes(networkModule)
    single<DataStore>()
    single<PostRepositoryImpl>() bind PostRepository::class
    single<GetPostsUseCase>()
    viewModel<HomeViewmodel>()
    viewModel<PostViewModel>()
}