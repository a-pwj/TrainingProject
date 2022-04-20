package com.training.project.test

import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @Author:          pwj
 * @Date:            2021/11/1 9:33
 * @FileName:        CustomViewModel
 * @Description:     ViewModel 携带参数
 */
class CustomViewModel {

    /**
     * 带参数的ViewModel （扩展版）
     *
     * 自定义viewModel 扩展，将传入的ViewModel实例对象直接使用，
     * 避免ViewModelProvider通过Factory 反射获取ViewModel实例
     *
     * 使用：
     *
     * val viewModel by viewModel { DataViewModel(1) }
     *
     *
     * @receiver FragmentActivity
     * @param factory Function0<VM>
     * @return Lazy<VM>
     */
    inline fun <reified VM : ViewModel> FragmentActivity.viewModel(noinline factory: () -> VM): Lazy<VM> =viewModels { ParamViewModelFactory(factory) }

    /**
     * 直接获取传入的实例 转化为 ViewModel
     * @param VM : ViewModel
     * @property factory Function0<VM>
     * @constructor
     */
    class ParamViewModelFactory<VM : ViewModel>(private val factory: () -> VM) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory() as T
        }
    }


    /**
     * 带参数的ViewModel （普通版）
     *
     * use example:
     *
     * 普通使用：
     * val viewModel = ViewModelProvider(activity, DataFactory(1)).get(DataViewModel::class.java)
     *
     * 在Activity 通过ViewModel扩展使用
     *     // 1. 使用 viewModels() 扩展实例化 ViewModel，
     *     // 在 implementation "androidx.activity:activity-ktx:1.3.1" 该库中
     * val viewModel by viewModels<CustomViewModel.DataViewModel> { CustomViewModel.DataFactory(1) }
     *
     * @property id Int
     * @constructor
     */
    class DataViewModel(val id: Int) : ViewModel()

    /**
     * 自定义Factory
     * @property id Int
     * @constructor
     */
    class DataFactory(private val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Int::class.java).newInstance(id) as T
        }
    }
}

