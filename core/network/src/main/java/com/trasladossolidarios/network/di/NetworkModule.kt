package com.trasladossolidarios.network.di

import com.google.firebase.firestore.FirebaseFirestore
import com.trasladossolidarios.network.cloudinary.datasource.CloudinaryDataSource
import com.trasladossolidarios.network.firestore.datasourse.RideRemoteDataSource
import com.trasladossolidarios.network.firestore.datasourse.UserFirestoreDataSource
import com.trasladossolidarios.network.firestore.datasourse.VolunteerFirestoreDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {

    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }

    single { CloudinaryDataSource(androidContext()) }

    single { RideRemoteDataSource(get()) }
    single { UserFirestoreDataSource(get()) }
    single { VolunteerFirestoreDataSource(get()) }
}
