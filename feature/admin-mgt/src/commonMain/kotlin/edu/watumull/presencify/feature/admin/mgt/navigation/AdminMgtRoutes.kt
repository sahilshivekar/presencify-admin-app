package edu.watumull.presencify.feature.admin.mgt.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

sealed interface AdminMgtRoutes : NavRoute {

    @Serializable
    data object AddAdmin : AdminMgtRoutes

    @Serializable
    data class UpdateAdminPassword(val adminId: String) : AdminMgtRoutes

    @Serializable
    data class AdminDetails(val adminId: String? = null) : AdminMgtRoutes

    @Serializable
    data object SearchAdmin : AdminMgtRoutes

}
