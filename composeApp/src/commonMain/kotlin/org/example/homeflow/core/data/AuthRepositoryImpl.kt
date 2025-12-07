package org.example.homeflow.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import co.touchlab.kermit.Logger
import com.sunildhiman90.kmauth.google.GoogleAuthManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import org.example.homeflow.core.data.repositories.AuthRepository
import org.example.homeflow.core.model.User

class AuthRepositoryImpl(
    private val googleAuthManager: GoogleAuthManager,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {


    override val isSignedIn: Flow<Boolean>
        get() = dataStore.data.map { pref -> pref[DataStoreKeys.USER_KEY] != null }

    override suspend fun getUser(): User {
        return Json.decodeFromString<User>(dataStore.data.map { it[DataStoreKeys.USER_KEY] }.first()!!)
    }


    override suspend fun login(): Result<Unit> {
        try {
            val result = googleAuthManager.signIn()
            result.fold(
                onSuccess = { kmaAuthUser ->
                    val user = Json.encodeToString(User(
                        name = kmaAuthUser?.name ?: "",
                        email = kmaAuthUser?.email ?: "",
                        phoneNumber = kmaAuthUser?.phoneNumber ?: "",
                        profilePicUrl = kmaAuthUser?.profilePicUrl ?: ""
                    ))
                    dataStore.updateData {
                        it.toMutablePreferences().also { pref ->
                            pref[DataStoreKeys.USER_KEY] = user
                        }
                    }
                    return Result.success(Unit)
                },
                onFailure = {
                    return Result.failure(it)
                }
            )
        } catch (e: Exception) {
            Logger.d(e.stackTraceToString())
            Logger.d("Exception while signing in with google")
            return Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        try {
            googleAuthManager.signOut()
            dataStore.updateData {
                it.toMutablePreferences().also { pref ->
                pref.remove(DataStoreKeys.USER_KEY)
            }}
            return Result.success(Unit)

        } catch (e: Exception) {
            Logger.d("Exception while sign out")
            Logger.d(e.stackTraceToString())
            return Result.failure(e)
        }
    }
}