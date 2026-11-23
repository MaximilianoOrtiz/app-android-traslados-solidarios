package com.trasladossolidarios.data.di

import com.trasladossolidarios.data.repository.FirebaseAuthRepositoryImpl
import com.trasladossolidarios.data.repository.ImageRepositoryImpl
import com.trasladossolidarios.data.repository.RideRepositoryImpl
import com.trasladossolidarios.data.repository.UserRepositoryImpl
import com.trasladossolidarios.data.repository.VolunteerRepositoryImpl
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import com.trasladossolidarios.domain.repository.ImageRepository
import com.trasladossolidarios.domain.repository.RidesRepository
import com.trasladossolidarios.domain.repository.UserRepository
import com.trasladossolidarios.domain.repository.VolunteerRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import org.koin.dsl.module

val dataModule = module {

    single<FirebaseAuth> { Firebase.auth }

    single<FirebaseAuthRepository> { FirebaseAuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<RidesRepository> { RideRepositoryImpl(get()) }
    single<VolunteerRepository> { VolunteerRepositoryImpl(get()) }
    single<ImageRepository> { ImageRepositoryImpl(get()) }

}
