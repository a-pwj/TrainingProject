package com.training.plugin.bp

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager

/**
 * @Author:          pwj
 * @Date:            2021/11/26 16:49
 * @FileName:        BuryPointTransform
 * @Description:     description
 */
class BuryPointTransform : Transform() {
    private val name = "BuryPoint"

    override fun getName(): String {
        return name
    }

    /**
     * 需要处理的数据类型，有两种枚举类型
     * CLASS->处理的java的class文件
     * RESOURCES->处理java的资源
     * @return
     */
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS

    }

    /**
     * 指 Transform 要操作内容的范围，官方文档 Scope 有 7 种类型：
     * 1. EXTERNAL_LIBRARIES        只有外部库
     * 2. PROJECT                   只有项目内容
     * 3. PROJECT_LOCAL_DEPS        只有项目的本地依赖(本地jar)
     * 4. PROVIDED_ONLY             只提供本地或远程依赖项
     * 5. SUB_PROJECTS              只有子项目。
     * 6. SUB_PROJECTS_LOCAL_DEPS   只有子项目的本地依赖项(本地jar)。
     * 7. TESTED_CODE               由当前变量(包括依赖项)测试的代码
     * @return
     */
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 是否增量编译
     * @return
     */
    override fun isIncremental(): Boolean {
        return false
    }

    /**
     *
     * @param context
     * @param inputs 有两种类型，一种是目录，一种是 jar 包，要分开遍历
     * @param outputProvider 输出路径
     */
    override fun transform(
        context: Context?,
        inputs: MutableCollection<TransformInput>?,
        referencedInputs: MutableCollection<TransformInput>?,
        outputProvider: TransformOutputProvider?,
        isIncremental: Boolean
    ) {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
        if (!isIncremental) {
            //不是增量更新，删除所有outputProvider
            outputProvider?.deleteAll()
        }
        inputs?.forEach { input ->
            //遍历目录
            input.directoryInputs.forEach { directoryInput ->
                handleDirectoryInput(directoryInput, outputProvider)
            }
            //遍历jar 第三方引入的class
            input.jarInputs.forEach { jarInput ->
//                handleJarInput(jarInput, outputProvider)
            }
        }
    }

    /**
     * 处理文件目录下的class文件
     * @param directoryInput DirectoryInput?
     * @param outputProvider TransformOutputProvider?
     */
    private fun handleDirectoryInput(directoryInput: DirectoryInput?, outputProvider: TransformOutputProvider?) {
        //是否是目录
        if (directoryInput?.file?.isDirectory == true) {
            //列出目录所有文件（包含文件夹，子文件夹内文件）
            directoryInput.file.walk().forEach { file ->
                val name = file.name
                if (filterClass(name)){

                }


            }

        }


    }

    /**
     * 检查class文件是否需要处理
     * @param name
     * @return
     */
    private fun filterClass(name: String): Boolean {
        return name.endsWith(".class") && !name.startsWith("R\$") && "R.class" != name && "BuildConfig.class" != name
    }
}