package com.trasladossolidarios.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// Cada pantalla es una "key" tipada: un objeto de Kotlin con sus argumentos como propiedades.
// El back stack es literalmente una lista de estas keys, por ejemplo [BookListDestination, BookDetailDestination(1342)].
// @Serializable permite guardar esa lista al rotar la pantalla o si el sistema mata el proceso.

@Serializable
data object AuthDestination : NavKey{
}
