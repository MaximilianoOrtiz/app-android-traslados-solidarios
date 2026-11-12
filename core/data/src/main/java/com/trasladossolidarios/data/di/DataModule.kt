package com.trasladossolidarios.data.di

import com.google.firebase.auth.FirebaseAuth
import com.trasladossolidarios.data.repository.FirebaseAuthRepositoryImpl
import com.trasladossolidarios.data.repository.RideRepositoryImpl
import com.trasladossolidarios.data.repository.UserRepositoryImpl
import com.trasladossolidarios.data.repository.VolunteerRepositoryImpl
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.repository.RidesRepository
import com.trasladossolidarios.domain.repository.UserRepository
import com.trasladossolidarios.domain.repository.VolunteerRepository
import org.koin.dsl.module

val dataModule = module {

    single<FirebaseAuth> { FirebaseAuth.getInstance() }

    single<FirebaseAuthRepository> { FirebaseAuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<RidesRepository> { RideRepositoryImpl(get()) }
    single<VolunteerRepository> { VolunteerRepositoryImpl(get()) }
}
