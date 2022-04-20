package com.training.project.ui.anim

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityAnimViewTestBinding
import com.training.project.ext.dp2px

class AnimViewTestActivity : BaseVBActivity<ActivityAnimViewTestBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.addView.setOnClickListener {
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(50F))
            mDataBinding.autoAnimLayout.addView(Button(this).apply { text = "button" }, layoutParams)
            mDataBinding.autoNormalLayout.addView(Button(this).apply { text = "button" }, layoutParams)
        }
        mDataBinding.removeView.setOnClickListener {
            if (mDataBinding.autoAnimLayout.childCount > 0) {
                mDataBinding.autoAnimLayout.removeViewAt(mDataBinding.autoAnimLayout.childCount - 1)
            }
            if (mDataBinding.autoNormalLayout.childCount > 0) {
                mDataBinding.autoNormalLayout.removeViewAt(mDataBinding.autoNormalLayout.childCount - 1)
            }
        }

    }
}