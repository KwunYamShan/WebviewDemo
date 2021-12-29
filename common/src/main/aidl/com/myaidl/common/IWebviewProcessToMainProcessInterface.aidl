// IWebviewProcessToMainProcessInterface.aidl
package com.myaidl.common;

// Declare any non-default types here with import statements

import com.myaidl.common.ICallbackFromMainprocessToWebViewProcessInterface;
/**
 * 1.js调用kotlin
 * 2.webview进程 跨进程调用app进程的命令能力
 * 3.app进程返回结果给webview进程
 * 4.kotlin回调js返回app结果
 */

//对应的是Command 第二步
interface IWebviewProcessToMainProcessInterface {
 void handleWebCommand(String commandName,String jsonParams,in ICallbackFromMainprocessToWebViewProcessInterface callback);
}
