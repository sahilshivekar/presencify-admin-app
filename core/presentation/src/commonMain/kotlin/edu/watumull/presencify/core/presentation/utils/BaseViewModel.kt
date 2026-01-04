package edu.watumull.presencify.core.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base ViewModel class providing common functionality for handling state, events, and actions.
 *
 * This class provides a standardized way to handle user actions, manage UI state,
 * and emit one-shot events in a unidirectional data flow pattern.
 */
abstract class BaseViewModel<S, E, A>(
    initialState: S
) : ViewModel() {

    protected val mutableStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    private val eventChannel: Channel<E> = Channel(capacity = Channel.UNLIMITED)
    private val actionChannel: Channel<A> = Channel(capacity = Channel.UNLIMITED)

    /**
     * A helper that returns the current state of the view model.
     */
    protected val state: S get() = mutableStateFlow.value

    /**
     * A [StateFlow] representing state updates.
     */
    val stateFlow: StateFlow<S> = mutableStateFlow.asStateFlow()

    /**
     * A [Flow] of one-shot events. These may be received and consumed by only a single consumer.
     * Any additional consumers will receive no events.
     */
    val eventFlow: Flow<E> = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            actionChannel
                .consumeAsFlow()
                .collect { action ->
                    handleAction(action)
                }
        }
    }

    /**
     * Handles the given [action] in a synchronous manner.
     *
     * Any changes to internal state that first require asynchronous work should post a follow-up
     * action that may be used to then update the state synchronously.
     */
    protected abstract fun handleAction(action: A)

    /**
     * Convenience method for sending an action to the [actionChannel].
     */
    fun trySendAction(action: A) {
        actionChannel.trySend(action)
    }

    /**
     * Convenience method for sending an event to the [eventChannel].
     */
    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    /**
     * Updates the current state using the provided update function.
     *
     * @param update A function that takes the current state and returns the new state.
     */
    protected fun updateState(update: (S) -> S) {
        mutableStateFlow.update(update)
    }

    /**
     * Sets the state to a new value.
     *
     * @param newState The new state value.
     */
    protected fun setState(newState: S) {
        mutableStateFlow.value = newState
    }
}