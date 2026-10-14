package com.trasladossolidarios.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : FirebaseAuthRepository {

    override suspend fun register(email: String,password: String): AppResult<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
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
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
                ?: return AppResult.Error(DomainError.Unknow)
            AppResult.Success(firebaseUser.toDomain())
        } catch (e: CancellationException) {throw e}
        catch (e : Exception) {
            AppResult.Error(e.toDomainError())
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): User? = firebaseAuth.currentUser?.toDomain()

    //mapea un objeto de la Firebase a User y asi ninguna otra capa conoce a Firebase
    private fun FirebaseUser.toDomain() = User(
        id = uid,
        email = email.orEmpty()
    )

    private fun Exception.toDomainError(): DomainError = when (this) {
        is FirebaseAuthWeakPasswordException -> DomainError.WeakPassword
        is FirebaseAuthUserCollisionException -> DomainError.EmailAlreadyInUse
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> DomainError.InvalidCredentials
        is FirebaseNetworkException -> DomainError.Network
        else -> DomainError.Unknow
    }
}