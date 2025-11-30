package org.example.homeflow.core.data.repositories

import kotlinx.coroutines.flow.Flow
import org.example.homeflow.core.model.House

interface HouseRepository {
    suspend fun createHouse(name: String): String
    suspend fun deleteHouse(id: String)
    suspend fun joinHouse(code: String, userId: String)
    fun getHouses(): Flow<List<House>>
    suspend fun getHouseById(id: String): House
}