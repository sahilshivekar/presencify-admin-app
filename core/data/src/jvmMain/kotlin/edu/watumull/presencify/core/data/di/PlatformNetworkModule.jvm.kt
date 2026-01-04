package edu.watumull.presencify.core.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformNetworkModule: Module
    get() = module {
        single<HttpClientEngine> { CIO.create() }
    }