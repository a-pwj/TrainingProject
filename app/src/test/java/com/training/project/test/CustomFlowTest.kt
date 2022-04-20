package com.training.project.test

import android.util.Log
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

/**
 * @Author:          pwj
 * @Date:            2021/11/1 13:59
 * @FileName:        CustomFlowTest
 * @Description:     description
 */
class CustomFlowTest : TestCase() {

    @InternalCoroutinesApi
    fun testCombineOperator() {
        GlobalScope.launch {
            val flow1 = flowOf(1, 2).onEach { delay(10) }
            val flow2 = flowOf("a", "b", "c").onEach { delay(20) }
            flow1.combine(flow2) { i, s -> i.toString() + s }.collect {
                Log.d("testCombineOperator","result=$it")
            }
        }

    }
}