package edu.watumull.presencify.core.presentation.global_snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
){

    LaunchedEffect(
        key1, key2, flow
    ) {
        flow.collect(onEvent)
    }

}