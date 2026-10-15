package com.trasladossolidarios

import android.app.Application
import com.trasladossolidarios.di.AppContainer

class ComposeTrasladosSolidariosApp : Application() {

    val container: AppContainer by lazy { AppContainer() }
}