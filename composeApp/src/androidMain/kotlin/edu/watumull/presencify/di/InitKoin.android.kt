package edu.watumull.presencify.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

fun initKoin(context: Context) {
    initKoin {
        androidLogger(Level.ERROR)
        androidContext(context)
    }
}
