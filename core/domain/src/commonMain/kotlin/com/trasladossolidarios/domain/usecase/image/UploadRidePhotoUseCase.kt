package com.trasladossolidarios.domain.usecase.image

import com.trasladossolidarios.domain.common.AppResult
import com.trasladossolidarios.domain.repository.ImageRepository

class UploadRidePhotoUseCase constructor(
    private val imageRepository: ImageRepository
){
    suspend operator fun invoke(
        imageUri: String
    ): AppResult<String> {
        return imageRepository.uploadImage(imageUri)
    }
}