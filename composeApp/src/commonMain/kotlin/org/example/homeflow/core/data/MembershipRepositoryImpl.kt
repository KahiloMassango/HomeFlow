package org.example.homeflow.core.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.homeflow.core.data.repositories.MembershipRepository
import org.example.homeflow.core.model.Membership
import org.example.homeflow.core.model.User
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MembershipRepositoryImpl : MembershipRepository {

    private val firestore = Firebase.firestore

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun createMembership(
        userId: String,
        houseId: String,
        username: String,
    ): Membership {
        val id = Uuid.random().toString()
        val membership = Membership(
            id = id,
            userId = userId,
            houseId = houseId,
            joinedAt = Clock.System.now().toEpochMilliseconds(),
            username = username
        )

        // Store in a dedicated memberships collection
        firestore.collection("memberships")
            .document(id)
            .set(membership)

        return membership
    }

    override suspend fun deleteMembership(membershipId: String) {
        firestore.collection("memberships")
            .document(membershipId)
            .delete()
    }


    override suspend fun getHouseMemberships(houseId: String): List<Membership> {
        val snapshot = firestore.collection("memberships")
            .where { "houseId".equalTo(houseId) }
            .get()

        return snapshot.documents.map {it.data<Membership>() }
    }

    override suspend fun getUserMemberships(userId: String): List<Membership> {
        val snapshot = firestore.collection("memberships")
            .where { "userId".equalTo(userId) }
            .get()

        return snapshot.documents.map { it.data<Membership>() }
    }

    override suspend fun getMembership(userId: String, houseId: String): Membership? {
        val snapshot = firestore.collection("memberships")
            .where {
                "userId".equalTo(userId) and
                "houseId".equalTo(houseId)
            }
            .get()

        return snapshot.documents.firstOrNull()?.data<Membership>()
    }

    override fun observeMembershipsByHouse(houseId: String): Flow<List<Membership>> =
        firestore.collection("memberships")
            .where { "houseId".equalTo(houseId) }
            .snapshots
            .map { it.documents }
            .map { doc -> doc.map { it.data<Membership>() } }


    override fun observeMembershipsByUser(userId: String): Flow<List<Membership>> {
        return firestore.collection("memberships")
            .where { "userId".equalTo(userId) }
            .snapshots
            .map { it.documents }
            .map { doc -> doc.map { it.data<Membership>() } }

    }
}