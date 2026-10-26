package com.trasladosolidarios.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rides")
data class RideEntity (
    @PrimaryKey val id: String,
    val userId: String,
    val petType: String,
    val petName: String,
    val petSize: String,
    val petDescription: String,
    val petPhotoUrl: String?,
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