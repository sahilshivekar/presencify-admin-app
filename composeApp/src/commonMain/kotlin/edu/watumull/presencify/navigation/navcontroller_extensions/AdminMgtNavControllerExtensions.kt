package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.admin.mgt.navigation.AdminMgtRoutes

/**
 * Navigate to Add Admin screen
 */
fun NavController.navigateToAddAdmin() {
    navigate(AdminMgtRoutes.AddAdmin)
}

/**
 * Navigate to Update Admin Password screen
 *
 * @param adminId The ID of the admin whose password is to be updated
 */
fun NavController.navigateToUpdateAdminPassword(adminId: String) {
    navigate(AdminMgtRoutes.UpdateAdminPassword(adminId = adminId))
}

/**
 * Navigate to Admin Details screen
 */
fun NavController.navigateToAdminDetails(adminId: String? = null) {
    navigate(AdminMgtRoutes.AdminDetails(adminId = adminId))
}

/**
 * Navigate to Search Admin screen
 */
fun NavController.navigateToSearchAdmin() {
    navigate(AdminMgtRoutes.SearchAdmin)
}

