package com.trasladossolidarios.network.cloudinary.datasource

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class CloudinaryDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun uploadImage(fileUri: Uri): String = suspendCancellableCoroutine { continuation ->
        val requestId = MediaManager.get().upload(fileUri)
            .unsigned("trasladossolidarios_preset") //fix?
            .callback(object : UploadCallback {
                override fun onStart(requestId: String) {}

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}

                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                    val url = resultData["secure_url"] as? String
                    if (url != null) {
                        if (continuation.isActive) continuation.resume(url)
                    } else {
                        if (continuation.isActive) continuation.resumeWithException(Exception("URL no encontrada en respuesta"))
                    }
                }

                override fun onError(requestId: String, error: ErrorInfo) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(Exception("Error de Cloudinary: ${error.description}"))
                    }
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(Exception("Subida reprogramada por falla de red"))
                    }
                }
            })
            .dispatch(context)

        // Cancela la subida en Cloudinary si la corrutina se cancela
        continuation.invokeOnCancellation {
            try {
                MediaManager.get().cancelRequest(requestId)
            } catch (_: Exception) {}
        }
    }
}