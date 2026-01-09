package edu.watumull.presencify.core.models

/**
 * Interface for objects that can provide a display label for dropdown menus and UI components.
 */
interface DisplayLabelProvider {
    fun toDisplayLabel(): String
}