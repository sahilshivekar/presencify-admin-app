package edu.watumull.presencify.core.data.local

actual fun dataStorePath(): String {
    // Not used on Android; DataStore is configured directly in platform storage module
    return ""
}