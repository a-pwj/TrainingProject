package com.training.project.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import com.noober.background.BackgroundLibrary
import java.lang.reflect.ParameterizedType


/**
 * @Author:          pwj
 * @Date:            2021/10/29 14:54
 * @FileName:        BaseVBActivity
 * @Description:     description
 */
abstract class BaseVBActivity<DB : ViewDataBinding> : BaseActivity() {

    //使用了DataBinding 就不需要 layoutId了，因为 会从DB泛型 找到相关的view
    override val layoutId: Int = 0

    lateinit var mDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        initDataBinding()
        super.onCreate(savedInstanceState)
        addContentView(savedInstanceState)
    }

    /**
     *  创建DataBinding
     */
    private fun initDataBinding() {
        reflexViewBinding()?.let {
            //在加载布局前，也就是调用inflate方法之前调用inject方法 shape替代 bl_标签名_标签属性名
            BackgroundLibrary.inject(this)
            mDataBinding = it
            dataBindView = mDataBinding.root
            mDataBinding.lifecycleOwner = this
        }
    }

    private fun reflexViewBinding(): DB? {
        try {
            //利用反射获取泛型
            val superClass = javaClass.genericSuperclass
            if (superClass != null && superClass is ParameterizedType) {
                val actualTypeArguments = superClass.actualTypeArguments
                actualTypeArguments.forEach { actualTypeArgument ->
                    val tClass = actualTypeArgument as Class<*>
                    if (ViewDataBinding::class.java.isAssignableFrom(tClass)) {
                        val method = tClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
                        return method.invoke(null, layoutInflater) as DB
                    }
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e.message, e)
        }
        return null
    }

    /**
     * 添加布局
     */
    private fun addContentView(savedInstanceState: Bundle?) {
        val container = window.decorView.findViewById<FrameLayout>(android.R.id.content)
        container.also {
            it.addView(if (dataBindView == null) LayoutInflater.from(this).inflate(layoutId, null) else dataBindView)
        }.post {
            initView(savedInstanceState)
        }
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

}