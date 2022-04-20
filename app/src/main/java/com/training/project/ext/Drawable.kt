package com.training.project.ext

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.oyelive.com.ApplicationBase
import java.util.*


/**
 * @Author:          pwj
 * @Date:            2022/4/18 14:52
 * @FileName:        Drawable
 * @Description:     description
 */

/**
 * 上圆角 ，下圆角
 * @param color Int
 * @param radius Float
 * @param isTopRadius Boolean
 * @return GradientDrawable?
 */
fun getTopRadiusDrawable(@ColorRes color: Int, radius: Float, isTopRadius: Boolean): GradientDrawable? {
    return GradientDrawable().apply {
        this.shape = GradientDrawable.RECTANGLE
        this.setColor(Color.parseColor(getHexColor(color)))
        val radiusF = dp2px(radius).toFloat()
        if (isTopRadius){
            this.cornerRadii = floatArrayOf(radiusF, radiusF, 0f, 0f)
        }else{
            this.cornerRadii = floatArrayOf(0f, 0f, radiusF, radiusF)
        }
    }
}

fun getShapeDrawable(@ColorRes color: Int, radius: Float): GradientDrawable? {
    return GradientDrawable().apply {
        this.shape = GradientDrawable.RECTANGLE
        this.setColor(Color.parseColor(getHexColor(color)))
        this.cornerRadius = dp2px(radius).toFloat()
    }
}

/**
 * 从左往右渐变
 * @param colors IntArray
 * @param radius Float
 * @return GradientDrawable?
 */
fun getGradientDrawable(colors: IntArray, radius: Float): GradientDrawable? {
    return GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        val intArray = IntArray(colors.size)
        colors.forEachIndexed { index, value ->
            intArray[index] = Color.parseColor(getHexColor(value))
        }
        setColors(intArray)
        gradientType = GradientDrawable.LINEAR_GRADIENT
        orientation = GradientDrawable.Orientation.LEFT_RIGHT
        cornerRadius = dp2px(radius).toFloat()
    }
}

/**
 * 传入 color id 返回16进制color
 */
fun getHexColor(id: Int): String {
    val result = StringBuilder()
    val color: Int = ContextCompat.getColor(ApplicationBase.getInstance(), id)
    result.append("#")
    result.append(intToHexValue(Color.red(color)))
    result.append(intToHexValue(Color.green(color)))
    result.append(intToHexValue(Color.blue(color)))
    return result.toString()
}

private fun intToHexValue(number: Int): String {
    val result = StringBuilder()
    result.append(Integer.toHexString(number and 0xff))
    while (result.length < 2) {
        result.append("0")
    }
    return result.toString().uppercase(Locale.getDefault())
}