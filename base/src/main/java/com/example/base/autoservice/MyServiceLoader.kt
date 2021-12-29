package com.example.base.autoservice

import java.lang.Exception
import java.util.*

object MyServiceLoader {
    fun <S> load(service: Class<S>?):S?{
        return try {
            ServiceLoader.load(service).iterator().next()//iterator 拿第一个
        }catch (e:Exception){
            null
        }
    }
}