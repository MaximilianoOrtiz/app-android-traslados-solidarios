package com.benitez_ortiz.ApMobileTP2

import android.app.Application

class HuellasApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}