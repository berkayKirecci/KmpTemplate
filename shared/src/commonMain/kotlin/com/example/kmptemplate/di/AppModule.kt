package com.example.kmptemplate.di

import com.example.kmptemplate.feature.home.ui.HomeViewmodel
import com.example.kmptemplate.feature.post.data.repository.PostRepository
import com.example.kmptemplate.feature.post.data.repository.PostRepositoryImpl
import com.example.kmptemplate.feature.post.domain.usecase.GetPostsUseCase
import com.example.kmptemplate.feature.post.ui.PostViewModel
import com.example.kmptemplate.network.networkModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(networkModule)
    singleOf(::PostRepositoryImpl) { bind<PostRepository>() }
    singleOf(::GetPostsUseCase)
    viewModelOf(::HomeViewmodel)
    viewModelOf(::PostViewModel)
}