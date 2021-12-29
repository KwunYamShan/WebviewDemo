package com.example.webviewdemo

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.base.BaseApplication
import com.example.common.autoservice.webviewcommand.Command
import com.google.auto.service.AutoService
import com.myaidl.common.ICallbackFromMainprocessToWebViewProcessInterface
//toast的js交互demo
@AutoService(Command::class)
class CommandShowToast :Command{
    override fun name(): String {
        return "showToast"
    }

    override fun execute(
        parameters: Map<*, *>,
        callback: ICallbackFromMainprocessToWebViewProcessInterface?
    ) {
        val handler = Handler(Looper.getMainLooper())
        handler.post{
            Toast.makeText(BaseApplication.sApplication,parameters["message"].toString(),Toast.LENGTH_SHORT).show()
        }
    }

}