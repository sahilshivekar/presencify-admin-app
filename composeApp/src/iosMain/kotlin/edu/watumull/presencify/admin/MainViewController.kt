package edu.watumull.presencify.admin

import androidx.compose.ui.window.ComposeUIViewController
import edu.watumull.presencify.admin.core.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }