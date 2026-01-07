package edu.watumull.presencify.core.domain.enums.admin

import kotlinx.serialization.SerialName

enum class AdminSortBy {
    @SerialName("username")
    USERNAME,
    @SerialName("email")
    EMAIL,
    @SerialName("createdAt")
    CREATED_AT,
    @SerialName("updatedAt")
    UPDATED_AT
}