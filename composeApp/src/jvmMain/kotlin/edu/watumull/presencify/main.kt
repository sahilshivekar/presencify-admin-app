package edu.watumull.presencify

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import edu.watumull.presencify.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Presencify",
    ) {
        App()
    }
}