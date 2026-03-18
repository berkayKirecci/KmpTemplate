package com.example.kmptemplate.post.di

import com.example.kmptemplate.post.data.repository.PostRepositoryImpl
import com.example.kmptemplate.post.domain.repository.PostRepository
import com.example.kmptemplate.post.domain.usecase.GetPostsUseCase
import com.example.kmptemplate.post.ui.PostRoute
import com.example.kmptemplate.post.ui.PostScreen
import com.example.kmptemplate.post.ui.PostViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.viewModel

@OptIn(KoinExperimentalAPI::class)
val postModule = module {
    single<PostRepositoryImpl>() bind PostRepository::class
    single<GetPostsUseCase>()
    viewModel<PostViewModel>()
    navigation<PostRoute> {
        PostScreen(koinViewModel())
    }
}

