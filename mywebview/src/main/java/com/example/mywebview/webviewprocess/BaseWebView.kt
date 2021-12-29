package com.example.mywebview.webviewprocess

import android.content.Context
import com.example.mywebview.webviewprocess.WebViewProcessCommandsDispacher.initAidlConnection
import com.example.mywebview.webviewprocess.settings.WebViewDefaultSettings.Companion.instance
import com.example.mywebview.webviewprocess.WebViewProcessCommandsDispacher.executeCommand
import android.webkit.WebView
import com.example.mywebview.WebViewCallBack
import com.example.mywebview.webviewprocess.webviewclient.MyWebViewClient
import com.example.mywebview.webviewprocess.webchromeclient.MyWebChromeClient
import android.webkit.JavascriptInterface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import com.example.mywebview.bean.JsParam
import com.google.gson.Gson

class BaseWebView : WebView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    ) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init() {
        initAidlConnection()
        instance.setSettings(this)
        addJavascriptInterface(
            this,
            "mycustomwebview"
        ) //把原生注入到webview里面去   takeNativeAction这个方法是mycustomwebview这个对象的
    }

    fun registerWebViewCallBack(webViewCallBack: WebViewCallBack?) {
        webViewClient = MyWebViewClient(webViewCallBack)
        webChromeClient = MyWebChromeClient(webViewCallBack)
    }

    @JavascriptInterface
    fun takeNativeAction(jsParam: String?) {
        Log.e(TAG, "takeNativeAction  jsParam ：$jsParam")
            val jsParamObject :JsParam ?= Gson().fromJson(jsParam, JsParam::class.java)
        jsParamObject?.let {
            executeCommand("${it.name}", Gson().toJson(jsParamObject.param), this)
        }
                //                if("showToast".equalsIgnoreCase(jsParamObject.name)) {
//                    Toast.makeText(getContext(), String.valueOf(new Gson().fromJson(jsParamObject.param, Map.class).get("message")), Toast.LENGTH_LONG).show();
//                }
    }

    fun handleCallback(callbackname: String?, response: String?) {
        if (!TextUtils.isEmpty(callbackname) && !TextUtils.isEmpty(response)) {
            post {
                val jscode = "javascript:mycustomjs.callback('$callbackname',$response)"
                evaluateJavascript(jscode, null)
            }
        }
    }

    companion object {
        @JvmField
        val TAG = BaseWebView::class.simpleName
    }
}