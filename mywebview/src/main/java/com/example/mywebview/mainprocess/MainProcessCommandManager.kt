package com.example.mywebview.mainprocess

import android.util.Log
import com.example.common.autoservice.webviewcommand.Command
import com.google.gson.Gson
import com.myaidl.common.ICallbackFromMainprocessToWebViewProcessInterface
import com.myaidl.common.IWebviewProcessToMainProcessInterface
import java.util.*
import kotlin.collections.HashMap

//命令管理器
object MainProcessCommandManager : IWebviewProcessToMainProcessInterface.Stub() {
    val TAG = MainProcessCommandManager::class.java.simpleName
    private val mCommands: HashMap<String, Command> = HashMap<String, Command>()
    init {
        val serviceLoader = ServiceLoader.load(Command::class.java)
        for(command in serviceLoader){
            if (!mCommands.containsKey(command.name())){
                mCommands[command.name()] = command
            }
        }
    }
    override fun handleWebCommand(
        commandName: String?,
        jsonParams: String?,
        callback: ICallbackFromMainprocessToWebViewProcessInterface?
    ) {
        Log.e(TAG,"Main process commands manager handle web command , commandName:$commandName ,jsonParams:$jsonParams")
         val msm= Gson().fromJson(jsonParams,MutableMap::class.java)

        mCommands[commandName]?.execute(msm,callback)
    }
}