package com.trasladossolidarios.location

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.trasladossolidarios.domain.location.LocationTracker
import com.trasladossolidarios.domain.model.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject

class LocationTrackerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationTracker {

    // --- 1. Obtención de ubicación física GPS ---
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
            Log.e("LocationTracker", "Error al obtener ubicación GPS", e)
            null
        }
    }

    // --- 2. Búsqueda de autocompletado global con OpenStreetMap / Nominatim ---
    override suspend fun searchAddresses(query: String): List<Location> = withContext(Dispatchers.IO) {
        val cleanQuery = query.trim()
        if (cleanQuery.length < 3) return@withContext emptyList()

        try {
            val encodedQuery = URLEncoder.encode(cleanQuery, "UTF-8")
            val urlString = "https://nominatim.openstreetmap.org/search?q=$encodedQuery&format=json&addressdetails=1&limit=5"
            val url = URL(urlString)

            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                // Nominatim exige identificar la app mediante un User-Agent
                setRequestProperty("User-Agent", "TrasladosSolidariosApp/1.0 (android-app)")
                connectTimeout = 5000
                readTimeout = 5000
            }

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val responseText = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(responseText)
                val resultList = mutableListOf<Location>()

                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    val displayName = item.optString("display_name", "")
                    val lat = item.optString("lat", "0.0").toDoubleOrNull() ?: 0.0
                    val lon = item.optString("lon", "0.0").toDoubleOrNull() ?: 0.0

                    resultList.add(
                        Location(
                            latitude = lat,
                            longitude = lon,
                            address = displayName
                        )
                    )
                }
                Log.d("LocationTracker", "Nominatim devolvió ${resultList.size} sugerencias para: '$cleanQuery'")
                resultList
            } else {
                Log.e("LocationTracker", "Nominatim devolvió respuesta HTTP: ${connection.responseCode}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("LocationTracker", "Error en consulta Nominatim para: '$cleanQuery'", e)
            emptyList()
        }
    }

    // --- 3. Geocodificación inversa con OpenStreetMap ---
    private suspend fun getAddressFromCoordinates(lat: Double, lng: Double): String? = withContext(Dispatchers.IO) {
        try {
            val urlString = "https://nominatim.openstreetmap.org/reverse?lat=$lat&lon=$lng&format=json"
            val url = URL(urlString)

            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("User-Agent", "TrasladosSolidariosApp/1.0 (android-app)")
                connectTimeout = 5000
                readTimeout = 5000
            }

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val responseText = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(responseText)
                jsonObject.optString("display_name", null)
            } else null
        } catch (e: Exception) {
            Log.e("LocationTracker", "Error en geocodificación inversa Nominatim", e)
            null
        }
    }
}