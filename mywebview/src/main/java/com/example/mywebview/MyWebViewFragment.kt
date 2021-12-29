package com.example.mywebview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.mywebview.databinding.FragmentMyWebviewBinding
import com.kingja.loadsir.core.LoadService
import com.example.base.loadsir.LoadingCallback

import com.kingja.loadsir.core.LoadSir


import androidx.databinding.DataBindingUtil

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import com.example.base.loadsir.ErrorCallback
import com.example.mywebview.webviewprocess.BaseWebView
import com.kingja.loadsir.callback.Callback
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener


const val WEBVIEW_FRAGMENT_URL = "url"
const val WEBVIEW_FRAGMENT_CAN_NATIVE_REFRESH = "can_native_refresh"
class MyWebViewFragment :Fragment(), OnRefreshListener, WebViewCallBack {
    private lateinit var mUrl:String
    private var mCanNativeRefresh:Boolean = true

    private  var  mIsError = false

    private lateinit var mBinding :FragmentMyWebviewBinding
    private lateinit var mLoadService :LoadService<*>
    companion object{
        fun newInstance(url:String?,canNativeRefresh:Boolean):MyWebViewFragment{
            val fragment = MyWebViewFragment()
            val bundle = Bundle()
            bundle.putString(WEBVIEW_FRAGMENT_URL,url);
            bundle.putBoolean(WEBVIEW_FRAGMENT_CAN_NATIVE_REFRESH,canNativeRefresh);
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        bundle?.let {
            mUrl = bundle.getString(WEBVIEW_FRAGMENT_URL)?:""
            mCanNativeRefresh = bundle.getBoolean(WEBVIEW_FRAGMENT_CAN_NATIVE_REFRESH,true)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_webview, container, false)
        mBinding.webview.registerWebViewCallBack(this)
        mBinding.webview.loadUrl(mUrl)
        mLoadService =
            LoadSir.getDefault().register(mBinding.smartrefreshlayout, object :
                Callback.OnReloadListener {
                override fun onReload(v: View?) {
//                    mLoadService.showCallback(LoadingCallback::class.java)
                    mBinding.webview.reload()
                }
            })
        mBinding.smartrefreshlayout.setOnRefreshListener(this)
        mBinding.smartrefreshlayout.setEnableRefresh(mCanNativeRefresh)
        mBinding.smartrefreshlayout.setEnableLoadMore(false)
        return mLoadService.getLoadLayout()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mBinding.webview.reload()
    }

    override fun pageStarted(url: String?) {
//            mLoadService.showCallback(LoadingCallback::class.java)
    }

    override fun pageFinished(url: String?) {
        if (mIsError) {
            mBinding.smartrefreshlayout.setEnableRefresh(true)
        } else {
            mBinding.smartrefreshlayout.setEnableRefresh(mCanNativeRefresh)
        }
        Log.d(BaseWebView.TAG, "pageFinished")
        mBinding.smartrefreshlayout.finishRefresh()
            if (mIsError) {
                mLoadService.showCallback(ErrorCallback::class.java)
            } else {
                mLoadService.showSuccess()
            }
        mIsError = false
    }

    override fun onError() {
        Log.e(BaseWebView.TAG, "onError");
        mIsError = true;
        mBinding.smartrefreshlayout.finishRefresh();
    }

    override fun updateTitle(title: String?) {
        if (activity is MyWebViewActivity) {
            (activity as MyWebViewActivity).updateTitle(title)
        }
    }

    fun goBack(){
       if ( mBinding.webview.canGoBack()){
           mBinding.webview.goBack()
       }
    }

}