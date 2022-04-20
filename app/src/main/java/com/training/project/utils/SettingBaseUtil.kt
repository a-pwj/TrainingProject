package com.training.project.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import com.tencent.mmkv.MMKV
import java.lang.reflect.InvocationTargetException
import kotlin.math.roundToInt

/**
 * 颜色设置类
 */
object SettingBaseUtil {


    /**
     * 获取列表动画模式
     */
    fun getListMode(): Int {
        val kv = MMKV.mmkvWithID("app")
        //0 关闭动画 1.渐显 2.缩放 3.从下到上 4.从左到右 5.从右到左
        return kv.decodeInt("mode", 2)
    }

    /**
     * 设置列表动画模式
     */
    fun setListMode(mode: Int) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("mode", mode)
    }

    /**
     * 设置shap文件的颜色
     *
     * @param view
     * @param color
     */
    fun setShapColor(view: View, color: Int) {
        val drawable = view.background as GradientDrawable
        drawable.setColor(color)
    }

    /**
     * 设置shap的渐变颜色
     */
    fun setShapColor(view: View, color: IntArray, orientation: GradientDrawable.Orientation) {
        val drawable = view.background as GradientDrawable
        drawable.orientation = orientation//渐变方向
        drawable.colors = color//渐变颜色数组
    }

    /**
     * 设置selector文件的颜色
     *
     * @param view
     * @param yesColor
     * @param noColor
     */
    fun setSelectorColor(view: View, yesColor: Int, noColor: Int) {
        val mySelectorGrad = view.background as StateListDrawable
        try {
            val slDraClass = StateListDrawable::class.java
            val getStateCountMethod = slDraClass.getDeclaredMethod("getStateCount", *arrayOfNulls(0))
            val getStateSetMethod = slDraClass.getDeclaredMethod("getStateSet", Int::class.javaPrimitiveType)
            val getDrawableMethod = slDraClass.getDeclaredMethod("getStateDrawable", Int::class.javaPrimitiveType)
            val count = getStateCountMethod.invoke(mySelectorGrad) as Int//对应item标签
            for (i in 0 until count) {
                val stateSet = getStateSetMethod.invoke(mySelectorGrad, i) as IntArray//对应item标签中的 android:state_xxxx
                if (stateSet.isEmpty()) {
                    val drawable = getDrawableMethod.invoke(mySelectorGrad, i) as GradientDrawable//这就是你要获得的Enabled为false时候的drawable
                    drawable.setColor(yesColor)
                } else {
                    for (j in stateSet.indices) {
                        val drawable = getDrawableMethod.invoke(mySelectorGrad, i) as GradientDrawable//这就是你要获得的Enabled为false时候的drawable
                        drawable.setColor(noColor)
                    }
                }
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * 设置颜色透明一半
     * @param color
     * @return
     */
    fun translucentColor(color: Int): Int {
        val factor = 0.5f
        val alpha = (Color.alpha(color) * factor).roundToInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}
