package com.example.mywebview

import android.content.Context
import android.content.Intent
import com.example.common.autoservice.MyWebViewService
import com.google.auto.service.AutoService

@AutoService(MyWebViewService::class)
class MyWebViewServiceImpl : MyWebViewService {
    override fun startWebViewActivity(context: Context, url: String,title:String,isShowActionBar:Boolean,isNativeRefresh:Boolean) {
        val intent = Intent(context,MyWebViewActivity::class.java)
        intent.putExtra(WEBVIEW_URL,url)
        intent.putExtra(WEBVIEW_TITLE,title)
        intent.putExtra(WEBVIEW_ISSHOW_ACTIONBAR,isShowActionBar)
        intent.putExtra(WEBVIEW_ISNATIVE_REFRESH,isNativeRefresh)
        context.startActivity(intent)
    }

    override fun getWebViewFragment(url: String, canNativeRefresh: Boolean) = MyWebViewFragment.newInstance(url,canNativeRefresh)


}