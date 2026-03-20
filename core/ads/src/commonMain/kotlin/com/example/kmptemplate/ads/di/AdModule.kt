package com.example.kmptemplate.ads.di

import com.example.kmptemplate.ads.AdManager
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val adModule = module {
    single<AdManager>()
}