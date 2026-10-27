package com.trasladossolidarios.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.trasladossolidarios.domain.location.LocationTracker
import com.trasladossolidarios.domain.model.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject

class LocationTrackerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationTracker {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        return try {
            val cancellationTokenSource = CancellationTokenSource()
            val androidLocation = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await() ?: return null

            val addressName = getAddressFromCoordinates(
                androidLocation.latitude,
                androidLocation.longitude
            )

            Location(
                latitude = androidLocation.latitude,
                longitude = androidLocation.longitude,
                address = addressName
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun getAddressFromCoordinates(lat: Double, lng: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            addresses?.firstOrNull()?.getAddressLine(0)
        } catch (e: Exception) {
            null
        }
    }
}