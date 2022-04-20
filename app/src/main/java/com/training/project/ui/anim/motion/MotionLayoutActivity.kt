package com.training.project.ui.anim.motion

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityMotionLayoutBinding

/**
 * @Author:          pwj
 * @Date:            2021/11/12 9:57
 * @FileName:        MotionLayoutActivity.kt
 * @Description:     MotionLayoutActivity.kt
 */
class MotionLayoutActivity : BaseVBActivity<ActivityMotionLayoutBinding>() {

    override fun initView(savedInstanceState: Bundle?) {



        hello()
    }


}


fun hello()= run { android.util.Log.d("TAG", "hello: .....") }

