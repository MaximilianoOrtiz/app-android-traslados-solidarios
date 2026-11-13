package com.trasladossolidarios.network.di

import com.trasladossolidarios.network.firestore.datasourse.RideRemoteDataSource
import com.trasladossolidarios.network.firestore.datasourse.UserFirestoreDataSource
import com.trasladossolidarios.network.firestore.datasourse.VolunteerFirestoreDataSource
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val networkModule = module {

    single<FirebaseFirestore> { Firebase.firestore }

    single { RideRemoteDataSource(get()) }
    single { UserFirestoreDataSource(get()) }
    single { VolunteerFirestoreDataSource(get()) }
}
