package org.example.homeflow.core.model

// UI model that includes member count
data class HouseWithMembers(
    val house: House,
    val memberCount: Int,
    val members: List<Membership> = emptyList(),
    val isOwner: Boolean
)

// Extension function for easy conversion
fun House.withMembers(memberships: List<Membership>, userId: String): HouseWithMembers {
    return HouseWithMembers(
        house = this,
        memberCount = memberships.size,
        members = memberships,
        isOwner = userId == this.ownerId
    )
}
