package com.training.plugin

import com.android.build.gradle.AppExtension
import com.training.plugin.bp.BuryPointEntity
import com.training.plugin.bp.BuryPointTransform
import com.training.plugin.mt.MethodTimerEntity
import com.training.plugin.mt.MethodTimerTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @Author:          pwj
 * @Date:            2021/11/26 16:45
 * @FileName:        StatisticPlugin
 * @Description:     自定义插桩 插件
 */
class StatisticPlugin : Plugin<Project> {

    companion object {
        var BURY_POINT_MAP: MutableMap<String, BuryPointEntity>? = null
        var METHOD_TIMER_LIST: MutableList<MethodTimerEntity>? = null
    }

    override fun apply(project: Project) {
        val android = project.extensions.findByType(AppExtension::class.java)
        //注册transform
        android?.registerTransform(BuryPointTransform())
        android?.registerTransform(MethodTimerTransform())
        //获取gradle里面配置的埋点信息
        val statisticExtension = project.extensions.create("statistic", StatisticExtension::class.java)
        project.afterEvaluate {
            //遍历配置的埋点信息，将其保存在BURY_POINT_MAP方便调用
            BURY_POINT_MAP = mutableMapOf()
            val buryPoint = statisticExtension.buryPoint
            if (buryPoint.size > 0) {
                buryPoint.forEach { map ->
                    val entity = BuryPointEntity()
                    if (map.containsKey("isAnnotation")) {
                        entity.isAnnotation = map["isAnnotation"] as Boolean
                    }
                    if (map.containsKey("isMethodExit")) {
                        entity.isMethodExit = map["isMethodExit"] as Boolean
                    }
                    if (map.containsKey("agentOwner")) {
                        entity.agentOwner = map["agentOwner"] as String
                    }
                    if (map.containsKey("agentName")) {
                        entity.agentName = map["agentName"] as String
                    }
                    if (map.containsKey("agentDesc")) {
                        entity.agentDesc = map["agentDesc"] as String
                    }
                    if (entity.isAnnotation) {
                        if (map.containsKey("annotationDesc")) {
                            entity.annotationDesc = map["annotationDesc"] as String
                        }
                        if (map.containsKey("annotationParams")) {
                            entity.annotationParams = map["annotationParams"] as LinkedHashMap<String, String>
                        }
                        BURY_POINT_MAP?.put(entity.annotationDesc, entity)
                    } else {
                        if (map.containsKey("methodOwner")) {
                            entity.methodOwner = map["methodOwner"] as String
                        }
                        if (map.containsKey("methodName")) {
                            entity.methodName = map["methodName"] as String
                        }
                        if (map.containsKey("methodDesc")) {
                            entity.methodDesc = map["methodDesc"] as String
                        }
                        BURY_POINT_MAP?.put(entity.methodName + entity.methodDesc, entity)
                    }
                }
            }

            //获取方法及时信息，将其保存在METHOD_TIMER_LIST方便调用
            METHOD_TIMER_LIST = mutableListOf()
            val methodTimer = statisticExtension.methodTimer
            if (methodTimer.size > 0) {
                methodTimer.forEach { map ->
                    val entity = MethodTimerEntity()
                    if (map.containsKey("time")) {
                        entity.time = map["time"] as Long
                    }
                    if (map.containsKey("owner")) {
                        entity.owner = map["owner"] as String
                    }
                    METHOD_TIMER_LIST?.add(entity)
                }
            }
        }
    }
}