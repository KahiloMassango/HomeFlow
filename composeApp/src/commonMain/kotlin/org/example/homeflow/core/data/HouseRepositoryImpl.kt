package org.example.homeflow.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import org.example.homeflow.core.data.repositories.HouseRepository
import org.example.homeflow.core.data.repositories.MembershipRepository
import org.example.homeflow.core.model.House
import org.example.homeflow.core.model.HouseWithMembers
import org.example.homeflow.core.model.User
import org.example.homeflow.core.model.withMembers
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class HouseRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val membershipRepository: MembershipRepository = MembershipRepositoryImpl()
) : HouseRepository {

    private val firestore = Firebase.firestore

    // Get logged-in user ID from DataStore
    private suspend fun getUserId(): String {
        return Json.decodeFromString<User>(dataStore.data.map { it[DataStoreKeys.USER_KEY] }.first()!!).email
    }

    private suspend fun getUsername(): String {
        return Json.decodeFromString<User>(dataStore.data.map { it[DataStoreKeys.USER_KEY] }.first()!!).name
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createHouse(name: String): String {
        val userId = getUserId()
        val id = Uuid.random().toString()
        val house = House(
            id = id,
            name = name,
            members = 1,
            ownerId = userId,
            code = id.take(6)
        )

        // Save house to a central houses collection
        firestore.collection("houses")
            .document(id)
            .set(house)

        // Create membership with "owner" role
        membershipRepository.createMembership(
            userId = userId,
            houseId = id,
            username = getUsername(),
        )

        return house.code
    }

    override suspend fun deleteHouse(id: String) {

        // Delete all memberships for this house
        val memberships = membershipRepository.getHouseMemberships(id)
        memberships.forEach { membershipRepository.deleteMembership(it.id) }

        // Delete the house
        firestore.collection("houses")
            .document(id)
            .delete()
    }

    override suspend fun joinHouse(code: String) {
        val userId = getUserId()

        // Find the house by code
        val querySnapshot = firestore.collection("houses")
            .where { "code".equalTo(code) }
            .get()

        if (querySnapshot.documents.isEmpty()) {
            throw IllegalArgumentException("House not found with code: $code")
        }

        val doc = querySnapshot.documents[0]
        val house = doc.data<House>()

        // Check if user is already a member
        val existingMembership = membershipRepository.getMembership(userId, house.id)
        if (existingMembership != null) {
            throw IllegalStateException("Already a member of this house")
        }

        // Create membership
        membershipRepository.createMembership(
            userId = userId,
            houseId = house.id,
            username = getUsername(),
        )

        // Update member count
        val memberCount = membershipRepository.getHouseMemberships(house.id).size
        val updatedHouse = house.copy(members = memberCount)
        firestore.collection("houses")
            .document(house.id)
            .set(updatedHouse)
    }

    override suspend fun getHouseById(id: String): House {
        val doc = firestore.collection("houses")
            .document(id)
            .get()

        return doc.data<House>()
    }

    override suspend fun getHouseByIdWithMembers(houseId: String): HouseWithMembers {
        val members = membershipRepository.getHouseMemberships(houseId)
        val house = firestore.collection("houses")
            .document(houseId)
            .get().data<House>()
        val userId = getUserId()
        return house.withMembers(members, userId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getHousesWithMembersFlow(): Flow<List<HouseWithMembers>> = flow {
        val userId = getUserId()

        // Listen to membership changes for the current user
        membershipRepository.observeMembershipsByUser(userId)
            .flatMapLatest { memberships ->
                val houseIds = memberships.map { it.houseId }

                if (houseIds.isEmpty()) {
                    return@flatMapLatest flowOf(emptyList())
                }

                // Combine real-time listeners for all houses with their members
                combine(
                    houseIds.map { houseId -> getHouseByIdWithMembersFlow(houseId) }
                ) { housesWithMembersArray ->
                    housesWithMembersArray.toList()
                }
            }
            .collect { emit(it) }
    }

    override fun getHouseByIdWithMembersFlow(houseId: String): Flow<HouseWithMembers> = flow {
        // Combine two real-time streams:
        // 1. House data changes
        // 2. Membership changes (joins, leaves, role updates)
        val userId = getUserId()
        combine(
            observeHouse(houseId),              // Flow<House?>
            membershipRepository.observeMembershipsByHouse(houseId)  // Flow<List<Membership>>
        ) { house, memberships ->
            // Create HouseWithMembers with current user info
            house.withMembers(memberships, userId)
        }.collect { emit(it) }
    }

    private fun observeHouse(houseId: String) =
         firestore.collection("houses")
            .document(houseId)
            .snapshots
            .map { it.data<House>() }



}
