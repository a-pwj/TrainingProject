package com.training.plugin.mt

import org.objectweb.asm.*


/**
 * @Author:          pwj
 * @Date:            2021/12/10 9:49
 * @FileName:        MethodTimerClassVisitor
 * @Description:
 *
 * ClassVisitor是用来生成asm和改变字节码的，ClassVisitor是一个访问字节码的框架，
 * 其对字节码的创建和修改主要是通过其内部的ClassVisitor具体实现来代理的
 */
class MethodTimerClassVisitor(classWriter: ClassWriter) : ClassVisitor(Opcodes.ASM5, classWriter) {

    private var methodOwner: String? = null

    /**
     * 访问类头部信息
     *
     * @param version 类版本
     * @param access 修饰符
     * @param name 类名
     * @param signature 泛型信息
     * @param superName 父类
     * @param interfaces 实现的接口
     */
    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.methodOwner
    }

    override fun visitEnd() {
        super.visitEnd()
    }

    /**
     * 扫描类的方法进行调用
     * @param access 修饰符
     * @param name 方法名字
     * @param desc 方法签名
     * @param signature 泛型信息
     * @param exceptions 抛出的异常
     * @return
     */
    override fun visitMethod(access: Int, name: String?, desc: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        var methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        if ((access and Opcodes.ACC_INTERFACE) == 0 && "<init>" != name && "<clinit>" != name) {
            methodVisitor = MethodTimerAdviceAdapter(api, methodVisitor, methodOwner, access, name, desc)
        }
        return methodVisitor
    }

    /**
     * 访问类的源文件.
     *
     * @param source 源文件名称
     * @param debug 附加的验证信息，可以为空
     */
    override fun visitSource(source: String?, debug: String?) {
        super.visitSource(source, debug)
    }

    /**
     * 访问类的注解
     *
     * @param desc   注解类的类描述
     * @param visible  runtime时期注解是否可以被访问
     * @return 返回一个注解值访问器
     */
    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        return super.visitAnnotation(desc, visible)
    }

    /**
     * 访问一个类的属性
     *
     * @param attr 类的属性
     */
    override fun visitAttribute(attr: Attribute?) {
        super.visitAttribute(attr)
    }

    /**
     * 访问内部类信息
     */
    override fun visitInnerClass(name: String?, outerName: String?, innerName: String?, access: Int) {
        super.visitInnerClass(name, outerName, innerName, access)
    }

    /**
     * 访问类的字段
     */
    override fun visitField(access: Int, name: String?, desc: String?, signature: String?, value: Any?): FieldVisitor {
        return super.visitField(access, name, desc, signature, value)
    }
}