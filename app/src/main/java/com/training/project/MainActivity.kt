package com.training.project

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.project.base.BaseVBActivity
import com.training.project.bean.OperationBean
import com.training.project.bean.OperationEnum
import com.training.project.databinding.ActivityMainBinding
import com.training.project.ext.init
import com.training.project.ext.toActivity
import com.training.project.ui.adapter.MainAdapter
import com.training.project.ui.anim.AnimViewTestActivity
import com.training.project.ui.anim.motion.MotionLayoutActivity
import com.training.project.ui.anim.spring.AnimSpringActivity
import com.training.project.ui.anim.transition.TransitionActivity
import com.training.project.ui.dialog.WelcomeDialogFragment
import com.training.project.ui.leak.DialogFragmentLeakActivity
import com.training.project.ui.opengl2.OpenGL2MainActivity
import com.training.project.ui.result_api.ResultApiActivity
import com.training.project.ui.shape.ShapeActivity
import com.training.project.ui.viewStub.ViewStubActivity
import com.training.project.ui.viewpager2.ViewPager2Activity
import com.training.project.utils.LeakTestUtils
import com.training.project.view.SpaceItemDecoration


class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    private val adapter by lazy { MainAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.recycler.init(LinearLayoutManager(this), adapter)
        mDataBinding.recycler.addItemDecoration(SpaceItemDecoration(0, 10, false))

        val list = arrayListOf<OperationBean>()
        //shape view
        list.add(OperationBean(OperationEnum.ACTIVITY_SHAPE) {
            toActivity<ShapeActivity>()
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out)
        })
        //spring anim
        list.add(OperationBean(OperationEnum.ACTIVITY_ANIM_SPRING) {
            toActivity<AnimSpringActivity>()
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out)
        })
        //motion anim
        list.add(OperationBean(OperationEnum.ACTIVITY_ANIM_MOTION) {
            toActivity<MotionLayoutActivity>()
        })
        // 布局自动添加动画
        list.add(OperationBean(OperationEnum.ACTIVITY_ANIM_TEST_VIEW) {
            toActivity<AnimViewTestActivity>()
        })
        //activity 进入 退出 共享动画
        list.add(OperationBean(OperationEnum.ACTIVITY_ANIM_ACTIVITY) {
            toActivity<TransitionActivity>()
        })
        //dialog 进入 退出动画
        list.add(OperationBean(OperationEnum.ACTIVITY_ANIM_DIALOG) {
            WelcomeDialogFragment().apply {
                isCancelable = false
                show(supportFragmentManager, fragmentTag)
            }
        })
        // view stub
        list.add(OperationBean(OperationEnum.ACTIVITY_VIEW_STUB) {
            toActivity<ViewStubActivity>()
        })
        // result api demo
        list.add(OperationBean(OperationEnum.RESULT_API) {
            toActivity<ResultApiActivity>()
        })
        // dialog fragment leak
        list.add(OperationBean(OperationEnum.ACTIVITY_DIALOG_FRAGMENT_LEAK) {
            toActivity<DialogFragmentLeakActivity>()
        })
        // opengles2
        list.add(OperationBean(OperationEnum.ACTIVITY_OPENGL_2) {
            toActivity<OpenGL2MainActivity>()
        })
        //ViewPager2
        list.add(OperationBean(OperationEnum.ACTIVITY_VIEWPAGER_2) {
            toActivity<ViewPager2Activity>()
        })
        adapter.setList(list)
        adapter.setOnItemClickListener { adapter, view, position ->
            list[position].action.invoke()
        }


        LeakTestUtils.setContext(this)
    }


}