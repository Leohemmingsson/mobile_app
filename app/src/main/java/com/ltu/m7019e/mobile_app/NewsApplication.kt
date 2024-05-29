package com.ltu.m7019e.mobile_app

import android.app.Application
import com.ltu.m7019e.mobile_app.database.AppContainer
import com.ltu.m7019e.mobile_app.database.DefaultAppContainer

class NewsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}