package com.training.project.test

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * @Author:          pwj
 * @Date:            2021/11/1 10:15
 * @FileName:        CustomFlow
 * @Description:     description
 */
class CustomFlow {

    /**
     * 冷热流：
     *
     * 冷流:下游无消费行为，上游不会产生数据，只有下游开始消费，上游才会开始产生数据
     * 热流：无论下游是否有消费行为，上游都会自己产生数据
     *
     * Flow 默认为冷流
     */

    /**
     * 创建
     */
    fun createFlow(): Flow<Int> {
        //第一种
        val flow = flow<Int> {
            for (i in 0..3) {
                emit(i)
            }
        }
        //第二种
        flowOf(1, 2, 3)
        //第三种
        listOf<Int>(1, 2, 3).asFlow()
        //创建空流
        emptyFlow<Int>()

        return flow
    }

    /**
     * 末端操作符
     */
    fun endOperator() {
        val TAG = "EndOperator"
        MainScope().launch {
            //collect
            createFlow().collect {
                Log.d(TAG, "collect =$it")
            }

            //collectIndexed
            createFlow().collectIndexed { index, value ->
                Log.d(TAG, "collectIndexed =${index.toString()}, value=$value")
            }

            //collectLatest 只保留当前最新的生产数据
            createFlow().collectLatest {
                Log.d(TAG, "collectLatest =$it")
            }
            //toCollection、toSet、toList 这些操作符用于将Flow转换为Collection、Set和List。


            //launchIn 在指定的协程作用域中直接执行Flow
            createFlow().launchIn(MainScope())

            //last、lastOrNull、first、firstOrNull
            //返回Flow的最后一个值（第一个值），区别是last为空的话，last会抛出异常，而lastOrNull可空。
            createFlow().last()
        }
    }

    /**
     * 状态操作符
     *
     * onStart：在上游生产数据前调用
     * onCompletion：在流完成或者取消时调用 , onCompletion也可以监听异常
     * onEach：在上游每次emit前调用
     * onEmpty：流中未产生任何数据时调用
     * catch：对上游中的异常进行捕获
     * retry、retryWhen：在发生异常时进行重试，retryWhen中可以拿到异常和当前重试的次数
     */
    fun statusOperator() {
        val TAG = "StatusOperator"
        MainScope().launch {
            createFlow().retryWhen { cause, attempt ->
                Log.d(TAG, "retryWhen = ${attempt.toString()}, cause = ${cause.message}")
                attempt <= 3
            }.onStart {
                Log.d(TAG, "onStart = ${Thread.currentThread().name}")
            }.onEach {
                Log.d(TAG, "emit value---$it")
            }.onCompletion { exception ->
                Log.d(TAG, "Result---$exception")
                Log.d(TAG, "Flow Complete")
            }.catch { error ->
                Log.d(TAG, "Flow Error $error")
            }.collect {
                Log.d(TAG, "Result---${it.toString()}")
            }
        }
    }

    /**
     * Transform操作符
     *
     * map、mapLatest、mapNotNull   map操作符将Flow的输入通过block转换为新的输出, map是一对一的变换
     *
     * transform、transformLatest   transform则可以完全控制流的数据，进行过滤、 重组等等操作都可以
     *
     * transformWhile transformWhile的返回值是一个bool类型，用来控制流的截断，如果返回true，则流继续执行，如果false，则流截断
     *
     */
    fun transformOperator() {
        val TAG = "TransformOperator"
        MainScope().launch {
            //map、mapLatest、mapNotNull   map操作符将Flow的输入通过block转换为新的输出, map是一对一的变换
            createFlow().map { it * it }

            //transform、transformLatest   transform则可以完全控制流的数据，进行过滤、 重组等等操作都可以
            createFlow().transform<Int, String> { emit("!!!$it!!!") }.collect { Log.d(TAG, "Result---${it.toString()}") }

            //transformWhile transformWhile的返回值是一个bool类型，用来控制流的截断，如果返回true，则流继续执行，如果false，则流截断
            createFlow().transformWhile<Int, Int> {
                emit(it)
                it >= 3
            }.collect { Log.d(TAG, "Result---${it.toString()}") }
        }
    }


    /**
     * 过滤操作符  用于过滤流中的数据
     *
     * filter、filterInstance、filterNot、filterNotNull  可以按条件、类型或者对过滤取反、取非空等条件进行操作。
     *
     * drop、dropWhile、take、takeWhile  可以丢弃前n个数据，或者是只拿前n个数据。带while后缀的，则表示按条件进行判断
     *
     * debounce  用于防抖，指定时间内的值只接收最新的一个
     *
     * sample sample操作符与debounce操作符有点像，但是却限制了一个周期性时间，sample操作符获取的是一个周期内的最新的数据，可以理解为debounce操作符增加了周期的限制
     *
     * distinctUntilChangedBy 去重操作符，可以按照指定类型的参数进行去重
     *
     */
    fun filterOperator() {
        val TAG = "FilterOperator"
        MainScope().launch {
            createFlow().filter { it % 2 == 0 }.collect { Log.d(TAG, "Result---${it.toString()}") }
        }
    }

    /**
     * 组合操作符
     *
     *  combine、combineTransform   combine操作符可以连接两个不同的Flow
     *
     *  merge  操作符用于将多个流合并。
     *
     *  zip操作符会分别从两个流中取值，当一个流中的数据取完，zip过程就完成了。
     */
    fun combineOperator() {
        val TAG = "CombineOperator"
        MainScope().launch {
            val flow1 = flowOf(1, 2).onEach { delay(10) }
            val flow2 = flowOf("a", "b", "c").onEach { delay(20) }
            flow1.combine(flow2) { i, s -> i.toString() + s }.collect {
                Log.d(TAG, "result=$it")
            }


            listOf(flow1, flow2).merge().collect {
                Log.d("xys", "Flow merge: $it")
            }

            flow1.zip(flow2) { i, s -> i.toString() + s }.collect {
                Log.d("xys", "Flow zip: $it")
            }
        }
    }


    /**
     * 线程切换
     */
    fun changeThread() {
        val TAG = "ChangeThread"
        MainScope().launch {
            createFlow().map { it * it }.flowOn(Dispatchers.IO).collect {
                Log.d(TAG, "Result---$it")
            }
        }
    }

    /**
     * 取消flow
     */
    fun cancelFlow() {
        val TAG = "ChangeThread"
        MainScope().launch {
            withTimeoutOrNull(2500) {
                flow<Int> {
                    for (i in 1..5) {
                        delay(1000)
                        emit(i)
                    }
                }.collect { Log.d(TAG, "Result---$it") }
            }

        }
    }

}