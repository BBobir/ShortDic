package com.humbur.shortdictionary

import android.annotation.SuppressLint
import android.app.Application

class App: Application() {

    companion object {
        lateinit var application: Application
    }

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        application = this
    }
}