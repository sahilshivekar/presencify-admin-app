package edu.watumull.presencify.core.domain.model.schedule

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import edu.watumull.presencify.core.domain.enums.RoomType

data class Room(
    val id: String,
    val roomNumber: String,
    val name: String? = null,
    val type: RoomType? = null,
    val sittingCapacity: Int,
    val classes: List<ClassSession>? = null
) : DisplayLabelProvider {

    override fun toDisplayLabel(): String = if (name != null) "$roomNumber - $name" else roomNumber
}
