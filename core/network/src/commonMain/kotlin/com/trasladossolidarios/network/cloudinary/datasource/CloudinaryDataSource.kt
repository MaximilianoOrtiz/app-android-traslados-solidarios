package com.trasladossolidarios.network.cloudinary.datasource

import com.trasladossolidarios.domain.common.AppResult

interface CloudinaryDataSource {
    suspend fun uploadImage(imageUri: String): String
}
