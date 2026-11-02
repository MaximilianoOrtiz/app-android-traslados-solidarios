package com.trasladosolidarios.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rides")
data class RideEntity (
    @PrimaryKey val id: String,
    val userId: String,
    val animalType: String,
    val animalame: String,
    val animalze: String,
    val animalDescription: String,
    val animalPhotoUrl: String?,
    val originAddress: String,
    val originLatitude: Double?,
    val originLongitude: Double?,
    val destinationAddress: String,
    val destinationLatitude: Double?,
    val destinationLongitude: Double?,
    val dateTimeMillis: Long,
    val specialNotes: String,
    val status: String,
    val volunteerId: String?,
)