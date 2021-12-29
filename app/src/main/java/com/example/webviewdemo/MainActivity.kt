package com.example.webviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.common.autoservice.MyWebViewService
import com.example.base.autoservice.MyServiceLoader
import com.example.webviewdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val myWebViewService = MyServiceLoader.load(MyWebViewService::class.java)
    lateinit var mBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mBinding.tvBtn.setOnClickListener{
            myWebViewService?.startWebViewActivity(this,"https://www.baidu.com")
        }
        mBinding.tvStartLocalDemoHtml.setOnClickListener{
            myWebViewService?.startWebViewActivity(this,"file:///android_asset/demo.html")
        }
    }
}