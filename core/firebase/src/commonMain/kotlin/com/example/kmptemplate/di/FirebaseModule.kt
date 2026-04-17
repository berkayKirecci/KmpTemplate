package com.example.kmptemplate.di

import com.example.kmptemplate.analytics.Analytics
import com.example.kmptemplate.auth.FirebaseAuth
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val firebaseModule = module {
    single<Analytics>()
    single<FirebaseAuth>()
}

