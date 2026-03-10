package com.example.kmptemplate.storage.di

import com.example.kmptemplate.storage.DataStore
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val storageModule = module {
    single<DataStore>()
}

