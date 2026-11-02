package com.trasladossolidarios.data.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.trasladossolidarios.data.mapper.toDomain
import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.Ride
import com.trasladossolidarios.domain.repository.RidesRepository
import com.trasladossolidarios.network.firestore.datasourse.RideRemoteDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RideRepositoryImpl @Inject constructor(
    private val rideRemoteDataSource: RideRemoteDataSource
): RidesRepository {

    override fun observeRidesByOwner(ownerId: String): Flow<AppResult<List<Ride>>> =
        rideRemoteDataSource.observeRidesByOwner(ownerId)
            .map { documents ->
                AppResult.Success(
                    documents.map { it.ride.toDomain(it.id) }
                ) as AppResult<List<Ride>>
            }
            .catch { emit(AppResult.Error(DomainError.Unknow))}


    override suspend fun observeAvailableRides(currentUserId: String): Flow<AppResult<List<Ride>>> {
        return rideRemoteDataSource
            .observeOpenRides()
            .map { documents ->
                val rides = documents
                    .map { document ->
                        document.ride.toDomain(document.id)
                    }
                    .filter { ride ->
                        ride.ownerId != currentUserId
                    }

                AppResult.Success(rides) as AppResult<List<Ride>>
            }
            .catch { exception ->
                val domainError = when (exception) {
                    is FirebaseFirestoreException -> {
                        when (exception.code) {
                            FirebaseFirestoreException.Code.UNAVAILABLE ->
                                DomainError.Network

                            else -> DomainError.Unknow
                        }
                    }

                    else -> DomainError.Unknow
                }

                emit(AppResult.Error(domainError))
            }
    }

    override suspend fun getRideById(rideId: String): AppResult<Ride> =
        try {
            val response = rideRemoteDataSource.getRideById(rideId)
                ?: return AppResult.Error(DomainError.NotFound)

            AppResult.Success(response.ride.toDomain(response.id))
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: FirebaseFirestoreException) {
            AppResult.Error(
                when (exception.code) {
                    FirebaseFirestoreException.Code.UNAVAILABLE -> DomainError.Network
                    else -> DomainError.Unknow
                }
            )
        } catch (exception: Exception) {
            AppResult.Error(DomainError.Unknow)
        }
}
