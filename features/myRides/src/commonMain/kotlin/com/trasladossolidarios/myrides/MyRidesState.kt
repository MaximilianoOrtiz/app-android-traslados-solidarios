package com.trasladossolidarios.myrides

import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.domain.model.Ride


enum class MyRidesTab { PUBLISHED, AS_VOLUNTEER }

sealed interface PublishedRidesState {
    data object Loading : PublishedRidesState
    data class Success(val rides: List<Ride>) : PublishedRidesState
    data class Error(val error: DomainError) : PublishedRidesState
}

data class MyRidesState(
    val selectedTab: MyRidesTab = MyRidesTab.PUBLISHED,
    val published: PublishedRidesState = PublishedRidesState.Loading,
) {
    val publishedCount: Int?
        get() = (published as? PublishedRidesState.Success)?.rides?.size
}