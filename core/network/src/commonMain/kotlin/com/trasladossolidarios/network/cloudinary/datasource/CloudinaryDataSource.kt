package com.trasladossolidarios.network.cloudinary.datasource

interface CloudinaryDataSource {
    suspend fun uploadImage(imageUri: String): String
}
