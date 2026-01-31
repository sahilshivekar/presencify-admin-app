package edu.watumull.presencify.core.presentation.components

/**
 * Feedback messages for list items.
 */
sealed class ListItemFeedback {
    data class Success(val message: String) : ListItemFeedback()
    data class Error(val message: String) : ListItemFeedback()
}
