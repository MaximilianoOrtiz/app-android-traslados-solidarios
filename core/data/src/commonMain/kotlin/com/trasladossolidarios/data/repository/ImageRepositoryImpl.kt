package com.trasladossolidarios.data.repository

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.repository.ImageRepository
import com.trasladossolidarios.network.cloudinary.datasource.CloudinaryDataSource

class ImageRepositoryImpl constructor(
    private val cloudinaryDataSource: CloudinaryDataSource
): ImageRepository {
    override suspend fun uploadImage(imageUri: String): AppResult<String> {
        val result: String = cloudinaryDataSource.uploadImage(imageUri)
        return AppResult.Success(result)
    }
}