package com.training.project.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.training.project.MainActivity
import com.training.project.R

/**
 * @Author:          pwj
 * @Date:            2021/11/15 9:27
 * @FileName:        SplashActivity.kt
 * @Description:     Splash Screen 展示示例
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this,MainActivity::class.java))
    }
}