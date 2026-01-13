package edu.watumull.presencify.core.data.di

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformNetworkModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }