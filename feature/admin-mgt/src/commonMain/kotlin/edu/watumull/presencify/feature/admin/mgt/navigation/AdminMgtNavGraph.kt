package edu.watumull.presencify.feature.admin.mgt.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions

fun NavGraphBuilder.adminMgtNavGraph() {

    composableWithSlideTransitions<AdminMgtRoutes.AddAdmin> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<AdminMgtRoutes.UpdateAdminPassword> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<AdminMgtRoutes.AdminDetails> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<AdminMgtRoutes.SearchAdmin> {
        // TODO: Add screen content
    }

}

