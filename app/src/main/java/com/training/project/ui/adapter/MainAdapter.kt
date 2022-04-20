package com.training.project.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.training.project.R
import com.training.project.bean.OperationBean
import com.training.project.databinding.ItemMainBinding

/**
 * @Author:          pwj
 * @Date:            2021/11/9 14:22
 * @FileName:        MainAdapter
 * @Description:     description
 */
class MainAdapter : BaseQuickAdapter<OperationBean, BaseDataBindingHolder<ItemMainBinding>>(R.layout.item_main) {
    override fun convert(holder: BaseDataBindingHolder<ItemMainBinding>, item: OperationBean) {
        holder.dataBinding?.data = item
    }
}