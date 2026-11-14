package com.trasladossolidarios.data.repository


import com.trasladossolidarios.data.mapper.toDomain
import com.trasladossolidarios.data.mapper.toDto
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.domain.repository.UserRepository
import com.trasladossolidarios.network.firestore.datasourse.UserFirestoreDataSource
import kotlin.coroutines.cancellation.CancellationException

class UserRepositoryImpl  constructor(
    private val userDataSourse: UserFirestoreDataSource
): UserRepository{

    override suspend fun saveUser(user: User): Result<Unit> {
        return try {
            userDataSourse.insert(user.uid, user.toDto())
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(userId: String): AppResult<User> {
        return try {
            val userDto = userDataSourse.getById(userId)
                ?: return AppResult.Error(DomainError.NotFound)

            val user = userDto.toDomain(userId)
            AppResult.Success(user)

        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            AppResult.Error(DomainError.Unknow)
        }
    }
}