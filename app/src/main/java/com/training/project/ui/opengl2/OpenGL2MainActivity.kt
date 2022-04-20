package com.training.project.ui.opengl2

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.project.base.BaseVBActivity
import com.training.project.bean.OperationBean
import com.training.project.bean.OperationEnum
import com.training.project.databinding.ActivityOpenGl2MainBinding
import com.training.project.ext.init
import com.training.project.ui.adapter.MainAdapter
import com.training.project.ui.opengl2.base.BaseRenderer
import com.training.project.ui.opengl2.render.*
import com.training.project.view.SpaceItemDecoration

/**
 * @Author:          pwj
 * @Date:            2022/1/25 10:06
 * @FileName:        OpenGL2MainActivity.kt
 * @Description:     OpenGL2MainActivity.kt
 */
class OpenGL2MainActivity : BaseVBActivity<ActivityOpenGl2MainBinding>() {

    private var glSurfaceView: GLSurfaceView? = null
    private val adapter by lazy { MainAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.recycler.init(LinearLayoutManager(this), adapter)
        mDataBinding.recycler.addItemDecoration(SpaceItemDecoration(0, 10, false))

        val list = arrayListOf<OperationBean>()
        //点
        list.add(OperationBean(OperationEnum.ACTIVITY_OPENGL_2_simple) {
            itemClick(OperationEnum.ACTIVITY_OPENGL_2_simple, RenderPoint(this))
        })
        list.add(OperationBean(OperationEnum.ACTIVITY_OPENGL_2_point) {
            itemClick(OperationEnum.ACTIVITY_OPENGL_2_point, RenderPoint2(this))
        })
        //线
        list.add(OperationBean(OperationEnum.ACTIVITY_OPENGL_2_shape_1) {
            itemClick(OperationEnum.ACTIVITY_OPENGL_2_shape_1, ShapeRenderer(this))
        })
        list.add(OperationBean(OperationEnum.ACTIVITY_OPENGL_2_shape_2) {
            itemClick(OperationEnum.ACTIVITY_OPENGL_2_shape_2, ShapeRenderer2(this))
        })
        //正交投影
        list.add(OperationBean(OperationEnum.ACTIVITY_OPENGL_2_ortho) {
            itemClick(OperationEnum.ACTIVITY_OPENGL_2_ortho, OrthoRenderer(this))
        })
        //渐变色
        list.add(OperationBean(OperationEnum.ACTIVITY_OPENGL_2_colorful) {
            itemClick(OperationEnum.ACTIVITY_OPENGL_2_colorful, ColorfulRenderer(this))
        })
        //渐变色
        list.add(OperationBean(OperationEnum.ACTIVITY_OPENGL_2_colorful_2) {
            itemClick(OperationEnum.ACTIVITY_OPENGL_2_colorful_2, ColorfulRenderer(this))
        })

        adapter.setList(list)
        adapter.setOnItemClickListener { adapter, view, position ->
            list[position].action.invoke()
        }
    }

    private fun itemClick(operation: OperationEnum, render: GLSurfaceView.Renderer) {
        when (operation) {
            OperationEnum.ACTIVITY_OPENGL_2_simple -> {
                //基础展示
            }
            OperationEnum.ACTIVITY_OPENGL_2_point -> {
                //绘制点
            }
            OperationEnum.ACTIVITY_OPENGL_2_shape_1 -> {
                //绘制图形
            }
            OperationEnum.ACTIVITY_OPENGL_2_shape_2 -> {
                //绘制多图形
            }

        }
        addSurfaceView(render)
    }

    private fun addSurfaceView(render: GLSurfaceView.Renderer) {
        glSurfaceView = GLSurfaceView(this)
        (mDataBinding.root as ViewGroup).addView(glSurfaceView)
        glSurfaceView!!.setEGLContextClientVersion(2)
        glSurfaceView!!.setEGLConfigChooser(false)

        glSurfaceView!!.setRenderer(render)
        glSurfaceView!!.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        glSurfaceView!!.setOnClickListener {
            glSurfaceView!!.requestRender()
            if (render is BaseRenderer) {
                render.onClick()
            }
        }
    }

    override fun onBackPressed() {
        if (glSurfaceView != null) {
            // 展示了GLSurfaceView，则删除列表之外的其余控件
            var childCount = mDataBinding.root.childCount
            var i = 0
            while (i < childCount) {
                if (mDataBinding.root.getChildAt(i) !== mDataBinding.recycler) {
                    mDataBinding.root.removeViewAt(i)
                    childCount--
                    i--
                }
                i++
            }
            glSurfaceView = null
        } else {
            super.onBackPressed()
        }
    }


    override fun onResume() {
        super.onResume()
        if (glSurfaceView != null) {
            glSurfaceView!!.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (glSurfaceView != null) {
            glSurfaceView!!.onPause()
        }
    }

}