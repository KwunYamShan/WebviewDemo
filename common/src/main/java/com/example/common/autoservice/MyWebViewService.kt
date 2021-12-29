package com.example.common.autoservice

import android.content.Context
import androidx.fragment.app.Fragment

interface MyWebViewService {
    fun startWebViewActivity(
        context: Context,
        url: String,
        title: String = "title",
        isShowActionBar: Boolean = true,
        isNativeRefresh:Boolean = true
    )

    fun getWebViewFragment(url: String, canNativeRefresh: Boolean = true): Fragment
}