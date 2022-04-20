package com.training.project.ui.opengl2.utils

import android.opengl.GLES20
import android.opengl.Matrix

/**
 * @Author:          pwj
 * @Date:            2022/2/10 15:37
 * @FileName:        ProjectionMatrixHelper
 * @Description:     正交投影矩阵助手类
 */
class ProjectionMatrixHelper(program: Int, name: String) {

    private val uMatrixLocation: Int = GLES20.glGetUniformLocation(program, name)

    /**
     * 矩阵数组
     */
    private val mProjectionMatrix = floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
    )

    fun enable(width: Int, height: Int) {
        //边长比(>=1),非宽高比
        val aspectRatio = if (width > height)
            width.toFloat() / height.toFloat()
        else
            height.toFloat() / width.toFloat()

        // 1. 矩阵数组
        // 2. 结果矩阵起始的偏移量
        // 3. left：x的最小值
        // 4. right：x的最大值
        // 5. bottom：y的最小值
        // 6. top：y的最大值
        // 7. near：z的最小值
        // 8. far：z的最大值
        if (width > height) {
            //横屏
            Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f)
        } else {
            //竖屏or正方形
            Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
        }
        //更新u_Matrix的值，即更新矩阵数组
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, mProjectionMatrix, 0)
    }
}