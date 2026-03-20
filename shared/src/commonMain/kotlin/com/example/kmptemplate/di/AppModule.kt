package com.example.kmptemplate.di

import com.example.kmptemplate.ads.di.adManager
import com.example.kmptemplate.home.di.homeModule
import com.example.kmptemplate.network.di.networkModule
import com.example.kmptemplate.post.di.postModule
import com.example.kmptemplate.storage.di.storageModule
import org.koin.dsl.module

val appModule = module {
    includes(
        listOf(
            networkModule,
            homeModule,
            postModule,
            storageModule,
            adManager
        )
    )
}