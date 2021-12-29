package com.example.webviewdemo

import android.content.ComponentName
import android.content.Intent
import com.example.base.BaseApplication
import com.example.common.autoservice.webviewcommand.Command
import com.google.auto.service.AutoService
import com.myaidl.common.ICallbackFromMainprocessToWebViewProcessInterface

//打开页面的js交互demo
@AutoService(Command::class)
class CommandOpenPage : Command {
    override fun name(): String {
        return "openPage"
    }

    override fun execute(
        parameters: Map<*, *>,
        callback: ICallbackFromMainprocessToWebViewProcessInterface?
    ) {
        val target_class = parameters.get("target_class").toString()
        val intent = Intent()
        intent.setComponent(
            ComponentName(
                BaseApplication.sApplication!!.applicationContext,
                target_class
            )
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        BaseApplication.sApplication?.startActivity(intent)
    }

}