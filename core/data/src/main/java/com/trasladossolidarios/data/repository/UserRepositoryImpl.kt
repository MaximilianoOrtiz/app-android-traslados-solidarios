package com.trasladossolidarios.data.repository

import android.util.Log
import com.trasladossolidarios.data.mapper.toDto
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.UserRepository
import com.trasladossolidarios.network.firestore.datasourse.UserFirestoreDataSource
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UserRepositoryImpl  @Inject constructor(
    private val userDataSourse: UserFirestoreDataSource
): UserRepository{

    override suspend fun saveUser(user: User): Result<Unit> {
        return try {
            Log.d("Huella", "Guardando usuario uid=${user.uid}")
            userDataSourse.insert(user.uid, user.toDto())
            Log.d("Huella", "Usuario guardado OK uid=${user.uid}")
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("Huella", "Fallo al guardar usuario", e)
            Result.failure(e)
        }
    }
}