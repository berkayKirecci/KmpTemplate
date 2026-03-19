package com.example.kmptemplate.home.di

import androidx.navigation3.runtime.NavKey
import com.example.kmptemplate.base.NavRouteSerializer
import com.example.kmptemplate.home.ui.HomeRoute
import com.example.kmptemplate.home.ui.HomeScreen
import com.example.kmptemplate.home.ui.HomeViewmodel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.viewModel

@OptIn(KoinExperimentalAPI::class)
val homeModule = module {
    viewModel<HomeViewmodel>()
    navigation<HomeRoute> {
        HomeScreen(koinViewModel())
    }

    single<NavRouteSerializer>(named("homeRouteSerializer")) {
        object : NavRouteSerializer {
            override val serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(HomeRoute::class, HomeRoute.serializer())
                }
            }
        }
    }
}
