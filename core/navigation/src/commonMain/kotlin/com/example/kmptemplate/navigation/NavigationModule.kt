package com.example.kmptemplate.navigation

import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val navigationModule = module {
    single<Navigator>()
}