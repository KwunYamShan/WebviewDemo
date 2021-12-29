package com.example.mywebview

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mywebview.databinding.ActivityMyWebviewBinding

const val WEBVIEW_URL = "url"
const val WEBVIEW_TITLE = "title"
const val WEBVIEW_ISSHOW_ACTIONBAR = "isShowActionBar"
const val WEBVIEW_ISNATIVE_REFRESH = "isNativeRefresh"
/**
 * 防止webview崩溃使app退出 可以将webview的activity放在独立进程中  减少oom的可能性
 * 独立进程是针对activity fragment不能设置到独立进程
 *       <activity
android:name=".MyWebViewActivity"
android:process=":myselfwebview"/>

 aidl进程通信
 */
class MyWebViewActivity :AppCompatActivity() {
    lateinit var mBinding :ActivityMyWebviewBinding
    lateinit var mUrl:String
    lateinit var mTitle:String
    var mIsSHowActionBar:Boolean = true
    var mIsNativeRefresh:Boolean = true
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_webview)



        mUrl = intent.getStringExtra(WEBVIEW_URL)?:""
        mTitle = intent.getStringExtra(WEBVIEW_TITLE)?:"title"
        mIsSHowActionBar = intent.getBooleanExtra(WEBVIEW_ISSHOW_ACTIONBAR,true)
        mIsNativeRefresh = intent.getBooleanExtra(WEBVIEW_ISNATIVE_REFRESH,true)

        if (mIsSHowActionBar)mBinding.libWebviewHeader.visibility = View.VISIBLE
        else mBinding.libWebviewHeader.visibility = View.GONE

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragment :MyWebViewFragment = MyWebViewFragment.newInstance(mUrl,mIsNativeRefresh)
        transaction.replace(R.id.fl_webview,fragment).commit()

        mBinding.libWebviewHeader.findViewById<TextView>(R.id.tv_title).text = mTitle
        mBinding.libWebviewHeader.findViewById<TextView>(R.id.tv_back).setOnClickListener{
            fragment.goBack()
        }
        mBinding.libWebviewHeader.findViewById<TextView>(R.id.tv_close).setOnClickListener{
            finish()
        }

    }

    fun updateTitle(title: String?) {
        mBinding.libWebviewHeader.findViewById<TextView>(R.id.tv_title).text =title
    }
}