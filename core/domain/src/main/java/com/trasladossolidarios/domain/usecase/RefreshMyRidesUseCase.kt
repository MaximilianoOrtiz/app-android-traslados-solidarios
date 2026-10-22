package com.trasladossolidarios.domain.usecase

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.repository.RidesRepository
import javax.inject.Inject

class RefreshMyRidesUseCase @Inject constructor(
    private val repository: RidesRepository,
) {
    suspend operator fun invoke(userId: String): AppResult<Unit> =
        repository.refreshMyRides(userId)
}
