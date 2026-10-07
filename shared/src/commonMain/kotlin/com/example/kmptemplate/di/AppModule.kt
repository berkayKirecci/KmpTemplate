package com.example.kmptemplate.di

import com.example.kmptemplate.ads.di.adModule
import com.example.kmptemplate.detail.di.detailModule
import com.example.kmptemplate.firebase.di.firebaseModule
import com.example.kmptemplate.navigation.navigationModule
import com.example.kmptemplate.network.di.networkModule
import com.example.kmptemplate.post.di.postModule
import com.example.kmptemplate.storage.di.storageModule
import org.koin.dsl.module

val appModule = module {
    includes(
        listOf(
            networkModule,
            detailModule,
            postModule,
            storageModule,
            navigationModule,
            adModule,
            firebaseModule
        )
    )
}