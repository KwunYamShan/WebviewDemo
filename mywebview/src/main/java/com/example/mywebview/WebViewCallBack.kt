package com.example.mywebview

interface WebViewCallBack {
    fun pageStarted(url: String?)
    fun pageFinished(url: String?)
    fun onError()
    fun updateTitle(title: String?)
}