package com.trasladossolidarios.domain.repository

import com.trasladossolidarios.domain.common.AppResult

interface ImageRepository {
    suspend fun uploadImage(imageUri: String): AppResult<String>
}