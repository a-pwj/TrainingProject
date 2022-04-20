package com.training.plugin.mt

import com.training.plugin.StatisticPlugin
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

/**
 * @Author:          pwj
 * @Date:            2021/12/10 10:21
 * @FileName:        MethodTimerAdviceAdapter.kt
 * @Description:
 *                  AdviceAdapter 是 MethodVisitor 的子类，使用 AdviceAdapter 可以更方便的修改方法的字节码。
 */
class MethodTimerAdviceAdapter(api: Int, methodVisitor: MethodVisitor?, private val methodOwner: String?, access: Int, private val name: String?, desc: String?) :
    AdviceAdapter(api, methodVisitor, access, name, desc) {

    private var slotIndex: Int = 0

    override fun onMethodEnter() {
        super.onMethodEnter()
        StatisticPlugin.METHOD_TIMER_LIST?.forEach { entity ->
            if (methodOwner?.contains(entity.owner) == true) {
                slotIndex = newLocal(Type.LONG_TYPE)
                mv.visitMethodInsn(Opcodes.INVOKEDYNAMIC, "java/lang/System", "currentTimeMillis", "()J", false)
                mv.visitVarInsn(LSTORE, slotIndex)
            }
        }
    }

    override fun onMethodExit(opcode: Int) {
        StatisticPlugin.METHOD_TIMER_LIST?.forEach { entity ->
            if (methodOwner?.contains(entity.owner) == true) {
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                mv.visitVarInsn(LLOAD, slotIndex)
                mv.visitInsn(LSUB)
                mv.visitVarInsn(LSTORE, slotIndex)
                mv.visitVarInsn(LLOAD, slotIndex)
                mv.visitLdcInsn(entity.time)
                mv.visitInsn(LCMP)
                val label0 = Label()
                mv.visitJumpInsn(IFLE, label0)
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
                mv.visitInsn(DUP)
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
                mv.visitLdcInsn(methodOwner + "/" + name + " --> execution time : (")
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                mv.visitVarInsn(LLOAD, slotIndex)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false)
                mv.visitLdcInsn("ms)")
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
                mv.visitLabel(label0)
            }
        }
        super.onMethodExit(opcode)
    }
}
