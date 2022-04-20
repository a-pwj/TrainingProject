package com.training.project

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources

class App : Application() {

    companion object {
        lateinit var appInstance: App
    }

    override fun getResources(): Resources {
        val res = super.getResources();
        if (res.configuration.fontScale != 1.0f) {//非默认值
            val newConfig = Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.displayMetrics);
        }
        return res;
    }


    override fun onCreate() {
        super.onCreate()
        appInstance = this

    }
}