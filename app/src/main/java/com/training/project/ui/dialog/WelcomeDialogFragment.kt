package com.training.project.ui.dialog

import android.content.DialogInterface
import android.view.Gravity
import android.view.animation.Animation
import androidx.core.os.bundleOf
import com.training.project.R
import com.training.project.base.BaseDialogFragment
import com.training.project.databinding.DialogWelcomeBinding
import com.training.project.ui.anim.dialog.FlipAnimation
import com.training.project.ui.anim.dialog.MoveAnimation
import com.training.project.ui.result_api.RESULT_BUNDLE_KEY
import com.training.project.ui.result_api.RESULT_KEY


/**
 * @Author:          pwj
 * @Date:            2021/11/25 10:01
 * @FileName:        WelcomeDialogFragment
 * @Description:     description
 */
class WelcomeDialogFragment : BaseDialogFragment<DialogWelcomeBinding>() {

    private val DURATION: Long = 500L

    override fun getStyle(): Int = R.style.BottomDialogNoAnim

    override fun getGravity(): Int = Gravity.CENTER

    override fun getCancelOutside(): Boolean = false

    override fun initView(dialogBinding: DialogWelcomeBinding?) {
        dialogBinding?.let {
            it.tvOk.setOnClickListener { dismissAllowingStateLoss() }
        }
        dialog?.let {
            it.window?.setWindowAnimations(R.style.BottomDialog_AnimationStyle2)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        parentFragmentManager.setFragmentResult(RESULT_KEY, bundleOf(RESULT_BUNDLE_KEY to "data form WelcomeDialogFragment"))
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (nextAnim == R.anim.anim_welcome_enter) {
            return MoveAnimation.create(MoveAnimation.LEFT, enter, DURATION)
        }
        if (nextAnim == R.anim.anim_welcome_exit) {
            return FlipAnimation.create(FlipAnimation.LEFT, enter, DURATION)
        }
        return null
    }


}