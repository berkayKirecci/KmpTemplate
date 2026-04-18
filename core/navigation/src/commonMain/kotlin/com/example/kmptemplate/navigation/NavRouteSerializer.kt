package com.example.kmptemplate.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.qualifier.named
import org.koin.dsl.module

interface NavRouteSerializer {
    val serializersModule: SerializersModule
}

inline fun <reified T : NavKey> navKeyModule(serializer: KSerializer<T>) = module {
    single<NavRouteSerializer>(named(T::class.simpleName ?: T::class.toString())) {
        object : NavRouteSerializer {
            override val serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(T::class, serializer)
                }
            }
        }
    }
}
