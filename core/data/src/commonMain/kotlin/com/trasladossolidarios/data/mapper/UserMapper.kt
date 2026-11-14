package com.trasladossolidarios.data.mapper

import com.trasladossolidarios.domain.model.User
import com.trasladossolidarios.network.firestore.dto.UserDto

fun User.toDto() = UserDto(
    firstName = firstName,
    lastName = lastName,
    email = email,
)

fun UserDto.toDomain(uid: String) = User(uid, firstName, lastName, email)