package com.example.kmptemplate.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module


@Module(includes = [ViewModelModule::class])
@Configuration
class AppModule

@ComponentScan("com.example.kmptemplate")
@Module
class ViewModelModule

@KoinApplication
object KoinApplication