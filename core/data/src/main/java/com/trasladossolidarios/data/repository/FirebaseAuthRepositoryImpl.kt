package com.trasladossolidarios.data.repository

import com.google.firebase.FirebaseNetworkException
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import dev.gitlive.firebase.auth.FirebaseAuthWeakPasswordException
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirebaseAuthRepositoryImpl constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {

    override suspend fun register(email: String,password: String): AppResult<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password)
            val  firebaseUser = result.user
                ?: return AppResult.Error(DomainError.Unknow)
            AppResult.Success(firebaseUser.toDomain())
            //si la pantalla se cierra las corrutinas se cancelan y lanzan la excepción
        } catch (e: CancellationException) {throw e}
        catch (e: Exception) {
            AppResult.Error(e.toDomainError())
        }
    }

    override suspend fun logIn(email: String, password: String): AppResult<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password)
            val firebaseUser = result.user
                ?: return AppResult.Error(DomainError.Unknow)
            AppResult.Success(firebaseUser.toDomain())
        } catch (e: CancellationException) {throw e}
        catch (e : Exception) {
            AppResult.Error(e.toDomainError())
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): User? = firebaseAuth.currentUser?.toDomain()

    //mapea un objeto de la Firebase a User y asi ninguna otra capa conoce a Firebase
    private fun FirebaseUser.toDomain() = User(
        uid = uid,
        firstName = "",
        lastName = "",
        email = email.orEmpty()
    )


    override fun observeAuthState(): Flow<User?> = firebaseAuth.authStateChanged.map { user ->
        user?.toDomain()
    }

    private fun Exception.toDomainError(): DomainError = when (this) {
        is FirebaseAuthWeakPasswordException -> DomainError.WeakPassword
        is FirebaseAuthUserCollisionException -> DomainError.EmailAlreadyInUse
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> DomainError.InvalidCredentials
        is FirebaseNetworkException -> DomainError.Network
        else -> DomainError.Unknow
    }
}
