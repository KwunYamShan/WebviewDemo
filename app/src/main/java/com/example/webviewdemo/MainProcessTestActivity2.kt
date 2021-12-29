package com.example.webviewdemo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mywebview.databinding.ActivityMyWebviewBinding
import com.example.webviewdemo.databinding.ActivityTestBinding

class MainProcessTestActivity2 :AppCompatActivity(){
    lateinit var mBinding : ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_test)
        mBinding.tvBtn.setText("测试页2")
    }
}