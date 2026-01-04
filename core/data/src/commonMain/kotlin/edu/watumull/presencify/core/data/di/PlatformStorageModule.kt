package edu.watumull.presencify.core.data.di

import org.koin.core.module.Module

// Platform-specific modules for storage (e.g., settings factories, ciphers)
expect val platformStorageModule: Module