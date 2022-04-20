package com.training.project.ui.viewpager2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @Author:          pwj
 * @Date:            2022/1/26 15:26
 * @FileName:        ViewPager2ViewModel
 * @Description:     description
 */
class ViewPager2ViewModel : ViewModel() {

    private val sharedFlow = MutableSharedFlow<Boolean>(0, 1, BufferOverflow.DROP_OLDEST)

    fun emitValue(value: Boolean) {
        viewModelScope.launch {
            sharedFlow.emit(value)
        }
    }

    fun getSharedFlow(): MutableSharedFlow<Boolean> {
        return sharedFlow
    }
}