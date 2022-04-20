package com.training.project.ui.opengl2.utils

import android.opengl.GLES20
import android.util.Log

/**
 * @Author:          pwj
 * @Date:            2022/1/24 16:53
 * @FileName:        ShaderHelper
 * @Description:     着色器助手类
 */
object ShaderHelper {
    private val TAG = ShaderHelper.javaClass.simpleName


    /**
     * 编译顶点着色器
     * @param shaderCode 编译代码
     * @return 着色器对象ID
     */
    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
    }

    /**
     * 编译片段着色器
     * @param shaderCode 编译代码
     * @return 着色器对象ID
     */
    fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
    }


    /**
     * 编译片段着色器
     *
     * @param type       着色器类型
     * @param shaderCode 编译代码
     * @return 着色器对象ID
     */
    private fun compileShader(type: Int, shaderCode: String): Int {
        //1.创建一个新的着色器
        val shaderObjectId = GLES20.glCreateShader(type)

        //2.获取创建状态
        if (shaderObjectId == 0) {
            //在OpenGl中，都是用过整型值去作为OpenGl对象的引用。之后进行操作的时候都是将这个整型值传回给OpenGl进行操作
            //返回值0代表着创建对象失败。
            Log.w(TAG, "Could not create new shader.")
            return 0
        }

        //3.将着色器代码上传到着色器对象中
        GLES20.glShaderSource(shaderObjectId, shaderCode)

        //4.编译着色器对象
        GLES20.glCompileShader(shaderObjectId)

        //5.获取编译状态，OpenGl将想要获取的值放入长度为1的数组的首位。
        val compilerStatus = IntArray(1)
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compilerStatus, 0)

        // 打印编译的着色器信息
        Log.w(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:" + GLES20.glGetShaderInfoLog(shaderObjectId))


        //6.验证编译状态
        if (compilerStatus[0] == 0) {
            //如果编译失败，则删除创建的着色器对象
            GLES20.glDeleteShader(shaderObjectId)
            Log.w(TAG, "Compilation of shader failed.")
            return 0
        }

        //7.返回着色器对象 成功 非0
        return shaderObjectId
    }

    /**
     * 创建OpenGL程序：通过链接顶点着色器、片段着色器
     *
     * @param vertexShaderId   顶点着色器ID
     * @param fragmentShaderId 片段着色器ID
     * @return OpenGL程序ID
     */
    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        //1.创建一个Opengl程序对象
        val programObjectId = GLES20.glCreateProgram()

        //2.获取创建状态
        if (programObjectId == 0) {
            Log.w(TAG, "Could not create new program")
            return 0
        }

        //3.将顶点着色器依附到Opengl程序对象
        GLES20.glAttachShader(programObjectId, vertexShaderId)
        //3.将片段着色器依附到Opengl程序对象
        GLES20.glAttachShader(programObjectId, fragmentShaderId)

        //4.将两个着色器链接到Opengl程序对象
        GLES20.glLinkProgram(programObjectId)

        //5.获取链接状态，Opengl将想要获取的值放入长度为1的数组的首位
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0)


        // 打印链接信息
        Log.w(TAG, "Results of linking program:\n" + GLES20.glGetProgramInfoLog(programObjectId))

        //6.验证链接状态
        if (linkStatus[0] == 0) {
            // 链接失败则删除程序对象
            GLES20.glDeleteProgram(programObjectId)
            Log.w(TAG, "Linking of program failed.")
            return 0
        }

        // 7.返回程序对象：成功，非0
        return programObjectId
    }

    /**
     * 验证OpenGL程序对象状态
     *
     * @param programObjectId OpenGL程序ID
     * @return 是否可用
     */
    fun validateProgram(programObjectId: Int): Boolean {
        GLES20.glValidateProgram(programObjectId)

        val validateStatus = IntArray(1)
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0)

        Log.v(TAG, "Results of validating program: " + validateStatus[0] + "\nLog:" + GLES20.glGetProgramInfoLog(programObjectId))

        return validateStatus[0] != 0
    }


}