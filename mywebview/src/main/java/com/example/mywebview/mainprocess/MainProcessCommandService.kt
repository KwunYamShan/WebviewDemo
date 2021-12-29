package com.example.mywebview.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MainProcessCommandService : Service() {
    //使用bind的方式  因为需要等待别人连接我们 而不是我们主动启动一个service
    override fun onBind(intent: Intent): IBinder? {
        return MainProcessCommandManager.asBinder()
    }
}