package com.example.webviewdemo

import android.os.RemoteException
import android.util.Log
import com.example.base.autoservice.MyServiceLoader
import com.google.auto.service.AutoService
import com.example.common.autoservice.IUserCenterService
import com.example.common.autoservice.webviewcommand.Command
import com.myaidl.common.ICallbackFromMainprocessToWebViewProcessInterface
import com.google.gson.Gson
import com.example.common.eventbus.LoginEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

@AutoService(Command::class)
class CommandLogin : Command {

    init {
        EventBus.getDefault().register(this)
    }

    var iUserCenterService = MyServiceLoader.load(
        IUserCenterService::class.java
    )
    var callback: ICallbackFromMainprocessToWebViewProcessInterface? = null
    var callbacknameFromNativeJs: String? = null
    override fun name(): String {
        return "login"
    }

    //ICallbackFromMainprocessToWebViewProcessInterface用于第一次回调 回调到webview进程
    override fun execute(
        parameters: Map<*, *>,
        callback: ICallbackFromMainprocessToWebViewProcessInterface?
    ) {
        Log.e("CommandLogin", Gson().toJson(parameters))
        iUserCenterService?.let {
            if (!it.isLogined) {
                it.login()
                this.callback = callback //app process callback webview process
                //用于第二次回调 回调到js
                callbacknameFromNativeJs = parameters["callbackname"].toString() //callback js
            }
        }

    }

    @Subscribe
    fun onMessageEvent(event: LoginEvent) {
        Log.d("CommandLogin", event.userName)
        val map: HashMap<String, String> = HashMap()
        map["accountName"] = event.userName
        try {
            callback?.onResult(callbacknameFromNativeJs, Gson().toJson(map))
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }
}