package com.trasladossolidarios.network.cloudinary.datasource

import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class CloudinaryDataSource @Inject constructor(
    @ApplicationContext private val context: Context
){
    suspend fun unploadImage(fileUri: Uri): String = suspendCancellableCoroutine { continuation ->
        MediaManager.get().upload(fileUri)
            .unsigned("_present")
            .callback(object: UploadCallback {
                override fun onStart(requestId: String) {}
                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long){}
                override fun onSuccess(requestId: String, resultData: Map<*,*>) {
                    val url = resultData["secure_url"] as? String
                    if (url != null) {
                        continuation.resume(url)
                    } else {
                        continuation.resumeWithException(Exception("URL no encontrada en respuesta"))
                    }
                }
                override fun onError(requestId:String, error: ErrorInfo) {
                    continuation.resumeWithException(Exception(error.description))
                }
                override fun onReschedule(requestId: String, error: ErrorInfo) {
                    continuation.resumeWithException(Exception("Subida reprogramada"))
                }
            })
    }
}