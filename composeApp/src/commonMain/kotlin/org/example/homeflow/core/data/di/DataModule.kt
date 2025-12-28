package org.example.homeflow.core.data.di

import com.sunildhiman90.kmauth.google.KMAuthGoogle.googleAuthManager
import org.example.homeflow.core.data.AuthRepositoryImpl
import org.example.homeflow.core.data.HouseRepositoryImpl
import org.example.homeflow.core.data.MembershipRepositoryImpl
import org.example.homeflow.core.data.TaskRepositoryImpl
import org.example.homeflow.core.data.repositories.AuthRepository
import org.example.homeflow.core.data.repositories.HouseRepository
import org.example.homeflow.core.data.repositories.MembershipRepository
import org.example.homeflow.core.data.repositories.TaskRepository
import org.example.homeflow.core.datastore.createPreferencesDataStore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single {
        googleAuthManager
    }

    single { createPreferencesDataStore() }

    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::HouseRepositoryImpl) { bind<HouseRepository>() }
    singleOf(::MembershipRepositoryImpl) { bind<MembershipRepository>() }
    singleOf(::TaskRepositoryImpl) { bind<TaskRepository>() }
}