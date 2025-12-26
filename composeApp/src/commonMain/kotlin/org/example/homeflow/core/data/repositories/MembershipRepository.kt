package org.example.homeflow.core.data.repositories

import kotlinx.coroutines.flow.Flow
import org.example.homeflow.core.model.Membership
import org.example.homeflow.core.model.User

interface MembershipRepository {
    suspend fun createMembership(userId: String, houseId: String, username: String): Membership
    suspend fun deleteMembership(membershipId: String)
    //suspend fun updateMembershipRole(membershipId: String, newRole: String)
    suspend fun getHouseMemberships(houseId: String): List<Membership>
    suspend fun getUserMemberships(userId: String): List<Membership>
    suspend fun getMembership(userId: String, houseId: String): Membership?
   fun observeMembershipsByHouse(houseId: String): Flow<List<Membership>>
    fun observeMembershipsByUser(userId: String): Flow<List<Membership>>
}