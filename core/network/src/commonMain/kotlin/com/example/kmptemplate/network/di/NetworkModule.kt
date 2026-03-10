package com.example.kmptemplate.network.di

import com.example.kmptemplate.network.createNetworkClient
import org.koin.dsl.module

val networkModule = module {
    single { createNetworkClient() }
}

