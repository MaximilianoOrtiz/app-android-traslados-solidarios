package com.trasladossolidarios.domain.model

data class User(
    val uid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
)

fun User.getFullName(): String = "$firstName $lastName".trim()