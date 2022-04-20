package com.training.plugin.bp

/**
 * @Author:          pwj
 * @Date:            2021/12/10 10:42
 * @FileName:        BuryPointEntity
 * @Description:     description
 */
class BuryPointEntity : Cloneable {
    //注解标识
    var isAnnotation = false

    // 方式插入时机 true 方法退出前 false 方法进入时
    var isMethodExit = false

    // Lambda表达式标识
    var isLambda = false

    //采集数据的方法的路径
    var agentOwner: String = ""

    //插入的方法的实现接口
    var agentName: String = ""

    /*
     * 采集数据的方法描述
     * isAnnotation = false（参数应在methodDesc范围之内）
     * isAnnotation = true（对照annotationParams，注意参数顺序）
     */
    var agentDesc: String = ""

    //插入的方法的路径
    var methodOwner: String = ""

    //插入的方法名
    var methodName: String = ""

    //插入的方法描述
    var methodDesc: String = ""

    //扫描的方法注解名称
    var annotationDesc: String = ""

    /*
     * String:注解参数名
     * String:参数类型
     */
    var annotationParams = LinkedHashMap<String, String>()

    /*
     * String:注解参数名
     * Object:参数值
     */
    var annotationData = HashMap<String, Any>()

    override fun clone(): Any {
        return super.clone() as BuryPointEntity
    }
}