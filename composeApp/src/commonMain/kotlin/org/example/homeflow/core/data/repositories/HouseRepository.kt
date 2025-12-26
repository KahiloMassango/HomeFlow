package org.example.homeflow.core.data.repositories

import kotlinx.coroutines.flow.Flow
import org.example.homeflow.core.model.House
import org.example.homeflow.core.model.HouseWithMembers

interface HouseRepository {
    suspend fun createHouse(name: String): String
    suspend fun deleteHouse(id: String)
    suspend fun joinHouse(code: String)
    fun getHouseByIdWithMembersFlow(houseId: String): Flow<HouseWithMembers>
    suspend fun getHouseByIdWithMembers(houseId: String): HouseWithMembers
    fun getHousesWithMembersFlow(): Flow<List<HouseWithMembers>>
    suspend fun getHouseById(id: String): House
}