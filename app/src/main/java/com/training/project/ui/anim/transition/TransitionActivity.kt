package com.training.project.ui.anim.transition

import android.os.Bundle
import android.view.Window
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityTransitionBinding

/**
 * @Author:          pwj
 * @Date:            2021/12/2 14:55
 * @FileName:        TransitionActivity.kt
 * @Description:
 *
 * Transition分为三种类型（android5.0中使用）。
 * 进入动画：Activity中的所有视图进入屏幕的动画。
 * 退出动画：Activity中的所有视图退出屏幕的动画。
 * 共享元素动画：利用共享的元素实现Activity的跳转动画。
 * 进入动画和退出动画合称Content Transition（内容变换动画），所以Transition分为内容变换动画和共享元素动画。
 *
 */
class TransitionActivity : BaseVBActivity<ActivityTransitionBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //启动Transition
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//        主题xml方式：
//        <item name="android:windowContentTransitions">true</item>

        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

}