package com.training.project.ui.anim.motion

import android.os.Bundle
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityMotionTestBinding


/**
 * @Author:          pwj
 * @Date:            2021/11/18 14:06
 * @FileName:        MotionTestActivity.kt
 * @Description:     motion  test
 */
class MotionTestActivity : BaseVBActivity<ActivityMotionTestBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

        mDataBinding.vStartStatus.setOnClickListener {
            mDataBinding.coverPk.transitionToEnd()
        }
        mDataBinding.vStartStatus1.setOnClickListener {
            mDataBinding.coverPk.transitionToStart()
        }
    }
}