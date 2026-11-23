package com.trasladossolidarios.domain.di

import com.trasladossolidarios.domain.usecase.auth.GetCurrentUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LoginUserUseCase
import com.trasladossolidarios.domain.usecase.auth.LogoutUseCase
import com.trasladossolidarios.domain.usecase.auth.ObserveAuthStateUseCase
import com.trasladossolidarios.domain.usecase.auth.RegisterUserUseCase
import com.trasladossolidarios.domain.usecase.image.UploadRidePhotoUseCase
import com.trasladossolidarios.domain.usecase.location.GetCurrentLocationUseCase
import com.trasladossolidarios.domain.usecase.location.SearchAddressesUseCase
import com.trasladossolidarios.domain.usecase.ride.CreateRideUseCase
import com.trasladossolidarios.domain.usecase.ride.GetRideDetailByIdUseCase
import com.trasladossolidarios.domain.usecase.ride.ObserveAvailableRidesUseCase
import com.trasladossolidarios.domain.usecase.ride.ObserveMyRidesUseCase
import com.trasladossolidarios.domain.usecase.user.GetCurrentUserIdUseCase
import com.trasladossolidarios.domain.usecase.volunteer.ApplyToRideUseCase
import com.trasladossolidarios.domain.usecase.volunteer.HasAppliedToRideUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {

    factoryOf(::GetCurrentUserUseCase)
    factoryOf(::LoginUserUseCase)
    factoryOf(::LogoutUseCase)
    factoryOf(::ObserveAuthStateUseCase)
    factoryOf(::RegisterUserUseCase)

    factoryOf(::GetCurrentUserIdUseCase)

    factoryOf(::CreateRideUseCase)
    factoryOf(::GetRideDetailByIdUseCase)
    factoryOf(::ObserveAvailableRidesUseCase)
    factoryOf(::ObserveMyRidesUseCase)

    factoryOf(::ApplyToRideUseCase)
    factoryOf(::HasAppliedToRideUseCase)

    factoryOf(::GetCurrentLocationUseCase)
    factoryOf(::SearchAddressesUseCase)

    factoryOf(::UploadRidePhotoUseCase)




}
