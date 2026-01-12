package org.example.homeflow.core.data.repositories

import kotlinx.coroutines.flow.Flow
import org.example.homeflow.core.model.House
import org.example.homeflow.core.model.HouseWithMembers
import org.example.homeflow.core.data.util.Result
interface HouseRepository {
    suspend fun createHouse(name: String): Result<String>
    suspend fun deleteHouse(id: String): Result<Unit>
    suspend fun joinHouse(code: String): Result<Unit>
    fun getHouseByIdWithMembersFlow(houseId: String): Flow<HouseWithMembers>
    suspend fun getHouseByIdWithMembers(houseId: String): Result<HouseWithMembers>
    fun getHousesWithMembersFlow(): Flow<List<HouseWithMembers>>
    suspend fun getHouseById(id: String): Result<House>

    suspend fun leaveHouse(houseId: String): Result<Unit>

}