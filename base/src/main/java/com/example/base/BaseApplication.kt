package com.example.base

import android.app.Application

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }

    companion object {
        var sApplication //所有组件可以引用 但是不要跟ui相关联
                : Application? = null
    }
}