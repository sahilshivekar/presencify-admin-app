package edu.watumull.presencify.core.design.systems.components

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import kotlin.jvm.JvmSuppressWildcards
import kotlin.reflect.KType

/**
 * Object containing predefined transition providers for navigation animations.
 */
object TransitionProviders {
    object Enter {
        val fadeIn: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
            { fadeIn() }
        val slideUp: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
            { slideInVertically { it } }
        val slideDown: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
            { slideInVertically { -it } }
        val pushLeft: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
            { slideInHorizontally { -it } }
        val pushRight: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
            { slideInHorizontally { it } }
        val stay: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
            { null }
    }

    object Exit {
        val fadeOut: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
            { fadeOut() }
        val slideUp: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
            { slideOutVertically { -it } }
        val slideDown: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
            { slideOutVertically { it } }
        val pushLeft: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
            { slideOutHorizontally { -it } }
        val pushRight: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
            { slideOutHorizontally { it } }
        val stay: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
            { null }
    }
}

fun NavGraphBuilder.composableWithFadeTransitions(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    this.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = TransitionProviders.Enter.fadeIn,
        exitTransition = TransitionProviders.Exit.fadeOut,
        popEnterTransition = TransitionProviders.Enter.fadeIn,
        popExitTransition = TransitionProviders.Exit.fadeOut,
        content = content,
    )
}

inline fun <reified T : Any> NavGraphBuilder.composableWithSlideTransitions(
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    this.composable<T>(
        typeMap = typeMap,
        deepLinks = deepLinks,
        enterTransition = TransitionProviders.Enter.slideUp,
        exitTransition = TransitionProviders.Exit.stay,
        popEnterTransition = TransitionProviders.Enter.stay,
        popExitTransition = TransitionProviders.Exit.slideDown,
        content = content
    )
}

fun NavGraphBuilder.composableWithStayTransitions(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    this.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = TransitionProviders.Enter.stay,
        exitTransition = TransitionProviders.Exit.stay,
        popEnterTransition = TransitionProviders.Enter.stay,
        popExitTransition = TransitionProviders.Exit.stay,
        content = content,
    )
}

fun NavGraphBuilder.composableWithPushTransitions(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    this.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = TransitionProviders.Enter.pushLeft,
        exitTransition = TransitionProviders.Exit.stay,
        popEnterTransition = TransitionProviders.Enter.stay,
        popExitTransition = TransitionProviders.Exit.pushRight,
        content = content,
    )
}

fun NavGraphBuilder.composableWithRootPushTransitions(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    this.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = TransitionProviders.Enter.stay,
        exitTransition = TransitionProviders.Exit.pushLeft,
        popEnterTransition = TransitionProviders.Enter.pushRight,
        popExitTransition = TransitionProviders.Exit.fadeOut,
        content = content,
    )
}