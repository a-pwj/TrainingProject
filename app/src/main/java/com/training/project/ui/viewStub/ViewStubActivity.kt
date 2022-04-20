package com.training.project.ui.viewStub

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.blankj.utilcode.util.ToastUtils
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityViewStubBinding

class ViewStubActivity : BaseVBActivity<ActivityViewStubBinding>() {

    private var button_view_stub: Button? = null

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.button.setOnClickListener {
            if (button_view_stub == null) {
                val viewStubProxy = mDataBinding.viewStub
                button_view_stub = viewStubProxy.viewStub?.inflate() as Button?
                ToastUtils.showShort("view stub inflate. id = " + button_view_stub!!.id)
            }
            ToastUtils.showShort("view stub inflate. isNULL = ${button_view_stub == null}")

            it.visibility = if (it.visibility == View.VISIBLE) View.GONE else View.VISIBLE

            button_view_stub?.setOnClickListener {
                button_view_stub?.let { button ->
                    button.visibility = if (button.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                    ToastUtils.showShort("view stub inflate. is_VISIBILTY = ${button.visibility}")
                }
            }
        }


    }
}