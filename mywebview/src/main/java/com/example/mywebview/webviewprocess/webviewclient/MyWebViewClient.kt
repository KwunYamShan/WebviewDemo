package com.example.mywebview.webviewprocess.webviewclient

import com.example.mywebview.WebViewCallBack
import android.webkit.WebViewClient
import android.webkit.WebView
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceError

//加载页面进度
class MyWebViewClient(private val mWebViewCallBack: WebViewCallBack?) : WebViewClient() {
    //加载开始
    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        if (mWebViewCallBack==null){
            Log.e(TAG, "WebViewCallBack is null.")
        }
        if (url==null){
            Log.e(TAG, "url is null.")
        }
        if (favicon==null){
            Log.e(TAG, "favicon is null.")
        }
        mWebViewCallBack?.pageStarted(url)
            ?: Log.e(TAG, "WebViewCallBack is null.")
    }

    //加载结束
    override fun onPageFinished(view: WebView, url: String) {
        mWebViewCallBack?.pageFinished(url)
            ?: Log.e(TAG, "WebViewCallBack is null.")
    }

    //发生错误
    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError
    ) {
        super.onReceivedError(view, request, error)
        mWebViewCallBack?.onError()
            ?: Log.e(TAG, "WebViewCallBack is null.")
    }

    companion object {
        private val TAG = MyWebViewClient::class.simpleName
    }
}