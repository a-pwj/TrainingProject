package com.training.project.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * @Author:          pwj
 * @Date:            2021/11/29 11:38
 * @FileName:        LeakTestUtils
 * @Description:     模拟内存泄露工具类
 */
@SuppressLint("StaticFieldLeak")
object LeakTestUtils {

    private var context: Context? = null


    fun setContext(context: Context) {
        this.context = context
    }

}