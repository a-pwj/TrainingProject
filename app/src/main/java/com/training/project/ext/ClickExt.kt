package com.training.project.ext

import android.app.Activity
import android.content.ContextWrapper
import android.os.SystemClock
import android.view.View
import com.training.project.R

/**
 * 作者　: hegaojian
 * 时间　: 2020/11/18
 * 描述　:
 */

/**
 * 设置防止重复点击事件
 * @param views 需要设置点击事件的view
 * @param interval 时间间隔 默认0.5秒
 * @param onClick 点击触发的方法
 */
fun setOnclickNoRepeat(vararg views: View?, interval: Long = 500, onClick: (View) -> Unit) {
    views.forEach {
        it?.clickNoRepeat(interval = interval) { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
var lastClickTime = 0L
var lastViewId = Int.MAX_VALUE
fun View.clickNoRepeat(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval && it.id == lastViewId)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        lastViewId = it.id
        action.invoke(it)
    }
}

fun View.clickNoRepeat(
    interval: Int = 500,
    isShareSingleClick: Boolean = true,
    listener: View.OnClickListener
) {
    val target = if (isShareSingleClick) getActivity(this)?.window?.decorView ?: this else this
    val millis = target.getTag(R.id.single_click_tag_last_click_millis) as? Long ?: 0
    if (SystemClock.uptimeMillis() - millis >= interval) {
        target.setTag(R.id.single_click_tag_last_click_millis, SystemClock.uptimeMillis())
        listener.onClick(this)
    }
}

private fun getActivity(view: View): Activity? {
    var context = view.context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

/**
 * 设置点击事件
 * @param views 需要设置点击事件的view
 * @param onClick 点击触发的方法
 */
fun setOnclick(vararg views: View?, onClick: (View) -> Unit) {
    views.forEach {
        it?.setOnClickListener { view ->
            onClick.invoke(view)
        }
    }
}