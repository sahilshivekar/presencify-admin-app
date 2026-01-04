package edu.watumull.presencify.core.data.di

import edu.watumull.presencify.core.data.network.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single { CIO.create() }
    single { HttpClientFactory(get()) }
}