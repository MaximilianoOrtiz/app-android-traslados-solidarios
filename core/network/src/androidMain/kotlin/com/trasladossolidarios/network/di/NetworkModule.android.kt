package com.trasladossolidarios.network.di

import com.trasladossolidarios.network.cloudinary.datasource.CloudinaryDataSource
import com.trasladossolidarios.network.cloudinary.datasource.CloudinaryDataSourceImpl

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModuleAndroid = module {

    single<CloudinaryDataSource> { CloudinaryDataSourceImpl(androidContext()) }

}
