package com.training.plugin.mt

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileOutputStream

/**
 * @Author:          pwj
 * @Date:            2021/12/10 9:24
 * @FileName:        MethodTimerTransform
 * @Description:     description
 */
class MethodTimerTransform : Transform() {
    private val name = "MethodTimer"

    /**
     * transform 名称
     * @return String
     */
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
     * 是否支持增量编译
     * @return Boolean
     */
    override fun isIncremental(): Boolean {
        return false
    }

    /**
     * transform方法的参数TransformInvocation是一个接口，提供一些关于输入的基本信息，利用这些接口就可以获得编译流程中的class文件进行操作
     *
     * @param context Context
     * @param inputs MutableCollection<TransformInput>              有两种类型，一种是目录，一种是 jar 包，要分开遍历
     * @param referencedInputs MutableCollection<TransformInput>
     * @param outputProvider TransformOutputProvider                输出路径
     * @param isIncremental Boolean                                 是否支持增量编译
     */
    override fun transform(
        context: Context?,
        inputs: MutableCollection<TransformInput>?,
        referencedInputs: MutableCollection<TransformInput>?,
        outputProvider: TransformOutputProvider?,
        isIncremental: Boolean
    ) {
        if (!isIncremental) {
            //不是增量更新，删除所有的outputProvider
            outputProvider?.deleteAll()
        }
        inputs?.forEach { input ->
            //遍历目录
            input.directoryInputs.forEach { directoryInput ->
                handleDirectoryInput(directoryInput, outputProvider)
            }
            //遍历jar 第三方引入的class
            input.jarInputs.forEach { jarInput ->
                handleJarInput(jarInput, outputProvider)
            }
        }
    }

    /**
     * 处理文件目录下的class文件
     *
     * @param directoryInput DirectoryInput?
     * @param outputProvider TransformOutputProvider?
     */
    private fun handleDirectoryInput(directoryInput: DirectoryInput?, outputProvider: TransformOutputProvider?) {
        //是否是目录
        if (directoryInput?.file?.isDirectory == true) {
            //列出目录所有文件（包含文件夹，子文件夹内文件）
            directoryInput.file.walk().forEach { file ->
                val name = file.name
                if (filterClass(name)) {
                    val classReader = ClassReader(file.readBytes())
                    /*
                    （1）new ClassWriter(0),表明需要手动计算栈帧大小、本地变量和操作数栈的大小；
                    （2）new ClassWriter(ClassWriter.COMPUTE_MAXS)需要自己计算栈帧大小，但本地变量与操作数已自动计算好，当然也可以调用visitMaxs方法，只不过不起作用，参数会被忽略；
                    （3）new ClassWriter(ClassWriter.COMPUTE_FRAMES)栈帧本地变量和操作数栈都自动计算，不需要调用visitFrame和visitMaxs方法，即使调用也会被忽略。
                     */
                    val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    val classVisitor = MethodTimerClassVisitor(classWriter)
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                    val code = classWriter.toByteArray()
                    val fos = FileOutputStream(file.parentFile.absolutePath + File.separator + name)
                    fos.write(code)
                    fos.close()
                }
            }
        }

        //文件夹里面包含的是我们首席的类以及R.class 、BuildConfig.class 以及R$XXX.class等
        //获取output目录
        val dest = outputProvider?.getContentLocation(directoryInput?.name, directoryInput?.contentTypes, directoryInput?.scopes, Format.DIRECTORY)
        //这里执行字节码的注入。不操作字节码的话也要将输入路径拷贝到输出路径
        FileUtils.copyDirectory(directoryInput?.file, dest)
    }


    /**
     * 检查class文件是否需要处理
     * @param name
     * @return
     */
    private fun filterClass(name: String): Boolean {
        return (name.endsWith(".class")
                && !name.startsWith("R\$")
                && "R.class" != name
                && "BuildConfig.class" != name)
    }
}