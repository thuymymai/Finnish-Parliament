package com.example.parliamentmembers.util

import android.app.Application
import android.content.Context

/*Name: My Mai, student ID: 2012197
Singleton class to get App context anywhere in the project
Date: 06/10/2021
*/

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}
