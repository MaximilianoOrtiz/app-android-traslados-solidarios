package com.trasladossolidarios.data.di

import com.google.firebase.auth.FirebaseAuth
import com.trasladossolidarios.data.repository.FirebaseAuthRepositoryImpl
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseAuthRepository(impl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository

    companion object {

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }
    }
}