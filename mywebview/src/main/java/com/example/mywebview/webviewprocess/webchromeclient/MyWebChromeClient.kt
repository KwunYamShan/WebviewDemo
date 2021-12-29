package com.example.mywebview.webviewprocess.webchromeclient

import android.util.Log
import com.example.mywebview.WebViewCallBack
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.ConsoleMessage

//监听浏览器标题   是在浏览器解析完后 获取浏览器标题
class MyWebChromeClient(private val mWebViewCallBack: WebViewCallBack?) : WebChromeClient() {
    override fun onReceivedTitle(view: WebView, title: String) {
//        if(mWebViewCallBack != null) {
//            mWebViewCallBack.updateTitle(title);
//        } else {
//            Log.e(TAG, "WebViewCallBack is null.");
//        }
        mWebViewCallBack?.updateTitle(title)
            ?: Log.e(TAG, "WebViewCallBack is null.")
    }

    /**
     * Report a JavaScript console message to the host application. The ChromeClient
     * should override this to process the log message as they see fit.
     * @param consoleMessage Object containing details of the console message.
     * @return `true` if the message is handled by the client.
     */
    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
        // Call the old version of this function for backwards compatability.
        Log.d(TAG, consoleMessage.message())
        return super.onConsoleMessage(consoleMessage)
    }

    companion object {
        private val TAG = MyWebChromeClient::class.java.simpleName
    }
}