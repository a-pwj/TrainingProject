package com.training.plugin.mt

/**
 * @Author:          pwj
 * @Date:            2021/12/10 10:36
 * @FileName:        MethodTimerEntity
 * @Description:     description
 */
class MethodTimerEntity : Cloneable {
    //时间过滤
    var time: Long = 0L

    //扫描的方法过滤
    var owner: String = ""


    override fun clone(): Any {
        return super.clone() as MethodTimerEntity
    }
}