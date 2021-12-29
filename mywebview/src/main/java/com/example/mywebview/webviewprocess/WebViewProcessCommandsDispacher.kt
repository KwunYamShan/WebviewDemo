package com.example.mywebview.webviewprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.base.BaseApplication
import com.example.mywebview.mainprocess.MainProcessCommandService
import com.myaidl.common.ICallbackFromMainprocessToWebViewProcessInterface
import com.myaidl.common.IWebviewProcessToMainProcessInterface

//分发器
object WebViewProcessCommandsDispacher : ServiceConnection {
    private var iWebviewProcessToMainProcessInterface: IWebviewProcessToMainProcessInterface? = null

    //建立连接
    fun initAidlConnection() {
        val intent = Intent(BaseApplication.sApplication, MainProcessCommandService::class.java)
        BaseApplication.sApplication?.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
        iWebviewProcessToMainProcessInterface =
            IWebviewProcessToMainProcessInterface.Stub.asInterface(iBinder)
    }

    override fun onServiceDisconnected(componentName: ComponentName?) {
        iWebviewProcessToMainProcessInterface = null
        initAidlConnection()
    }

    /**
     * 执行命令
     * @param commandName 命令名
     * @param parameters 命令参数
     * @param baseWebView 用于回调
     */
    fun executeCommand(commandName: String, parameters: String, baseWebView: BaseWebView) {
        iWebviewProcessToMainProcessInterface?.handleWebCommand(commandName, parameters, object :
            ICallbackFromMainprocessToWebViewProcessInterface.Stub() {
            override fun onResult(callbackname: String?, response: String?) {
                Log.e("executeCommand","callbackname：$callbackname ,response:$response")
                baseWebView.handleCallback(callbackname,response)
            }

        })
    }
}