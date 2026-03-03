package com.example.kmptemplate.di

import com.example.kmptemplate.feature.home.ui.HomeViewmodel
import com.example.kmptemplate.feature.post.data.repository.PostRepository
import com.example.kmptemplate.feature.post.data.repository.PostRepositoryImpl
import com.example.kmptemplate.feature.post.domain.usecase.GetPostsUseCase
import com.example.kmptemplate.feature.post.ui.PostViewModel
import com.example.kmptemplate.network.networkModule
import com.example.kmptemplate.platform.DataStore
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.viewModel

val appModule = module {
    includes(networkModule)
    single<DataStore>()
    single<PostRepositoryImpl>() bind PostRepository::class
    single<GetPostsUseCase>()
    viewModel<HomeViewmodel>()
    viewModel<PostViewModel>()
}