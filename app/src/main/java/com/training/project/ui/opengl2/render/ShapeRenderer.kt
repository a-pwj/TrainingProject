package com.training.project.ui.opengl2.render

import android.content.Context
import android.opengl.GLES20
import com.training.project.ui.opengl2.base.BaseRenderer
import com.training.project.ui.opengl2.utils.BufferUtil
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @Author:          pwj
 * @Date:            2022/1/25 11:08
 * @FileName:        ShapeRenderer
 * @Description:     图形
 */
class ShapeRenderer(context: Context) : BaseRenderer(context) {

    companion object {

        /**
         * 顶点着色器
         */
        private val VERTEX_SHADER = "" +
                "attribute vec4 a_Position;\n" +
                "void main()\n" +
                "{\n" +
                "    gl_Position = a_Position;\n" +
                "    gl_PointSize = 30.0;\n" +
                "}"

        /**
         * 片段着色器
         */
        private val FRAGMENT_SHADER = "" +
                "precision mediump float;\n" +
                "uniform vec4 u_Color;\n" +
                "void main()\n" +
                "{\n" +
                "    gl_FragColor = u_Color;\n" +
                "}"

        /**
         * 顶点数据数组
         */
        private val POINT_DATA = floatArrayOf(
            //两个点的x,y坐标(x,y 各占1个分量)
            0f, 0f,
            0f, 0.5f,
            -0.5f, 0f,
            0f, 0f - 0.5f,
            0.5f, 0f - 0.5f,
            0.5f, 0.5f - 0.5f
        )
        private const val POSITION_COMPONENT_COUNT = 2
        private val DRAW_COUNT = POINT_DATA.size / POSITION_COMPONENT_COUNT
    }

    private val vertexData = BufferUtil.createFloatBuffer(POINT_DATA)
    private var uColorLocation: Int = 0
    private var aPositionLocation: Int = 0
    private var drawIndex = 0


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        makeProgram(VERTEX_SHADER, FRAGMENT_SHADER)

        uColorLocation = getUniform("u_Color")
        aPositionLocation = getAttrib("a_Position")

        vertexData.position(0)
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData)
        GLES20.glEnableVertexAttribArray(aPositionLocation)

        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        drawIndex++
        drawTriangle()
        drawLine()
        drawPoint()
        if (drawIndex >= DRAW_COUNT) {
            drawIndex = 0
        }
    }

    private fun drawPoint() {
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, drawIndex)
    }

    private fun drawLine() {
        // GL_LINES：每2个点构成一条线段
        // GL_LINE_LOOP：按顺序将所有的点连接起来，包括首位相连
        // GL_LINE_STRIP：按顺序将所有的点连接起来，不包括首位相连
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, drawIndex)
    }

    private fun drawTriangle() {
        // GL_TRIANGLES：每3个点构成一个三角形
        // GL_TRIANGLE_STRIP：相邻3个点构成一个三角形,不包括首位两个点
        // GL_TRIANGLE_FAN：第一个点和之后所有相邻的2个点构成一个三角形
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, drawIndex)

    }
}