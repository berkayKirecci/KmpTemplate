package com.example.kmptemplate.post.di

import androidx.navigation3.runtime.NavKey
import com.example.kmptemplate.base.NavRouteSerializer
import com.example.kmptemplate.post.data.repository.PostRepositoryImpl
import com.example.kmptemplate.post.domain.repository.PostRepository
import com.example.kmptemplate.post.domain.usecase.GetPostsUseCase
import com.example.kmptemplate.post.ui.PostRoute
import com.example.kmptemplate.post.ui.PostScreen
import com.example.kmptemplate.post.ui.PostViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.qualifier.named
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

    single<NavRouteSerializer>(named("postRouteSerializer")) {
        object : NavRouteSerializer {
            override val serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(PostRoute::class, PostRoute.serializer())
                }
            }
        }
    }
}
