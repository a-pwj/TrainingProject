package com.training.project.ui.leak

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.training.project.R
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityDialogFragmentLeakBinding
import com.training.project.ext.toActivity
import com.training.project.ui.dialog.BaseDialogFragment

/**
 * @Author:          pwj
 * @Date:            2021/12/14 15:33
 * @FileName:        DialogFragmentLeakActivity.kt
 * @Description:     测试dialogFragment内存泄露
 */
class DialogFragmentLeakActivity : BaseVBActivity<ActivityDialogFragmentLeakBinding>() {

    private var dialogFragment: BaseDialogFragment? = null

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.button1.setOnClickListener {
            if (dialogFragment == null) {
                dialogFragment = BaseDialogFragment(R.layout.dialog_fragment_simple)
            }
            dialogFragment?.let {
                it.show(supportFragmentManager, it::class.java.simpleName)
            }
//            dialogFragment?.dialog?.setOnShowListener {
//                dialogFragment?.dialog?.findViewById<View>(R.id.text)?.setOnClickListener { toActivity<DialogFragmentLeakActivity>() }
//            }
        }
    }
}