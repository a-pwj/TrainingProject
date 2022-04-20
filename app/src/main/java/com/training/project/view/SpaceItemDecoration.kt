package com.training.project.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author:          pwj
 * @Date:            2021/11/12 13:41
 * @FileName:        SpaceItemDecoration.kt
 * @Description:     SpaceItemDecoration.kt
 */
class SpaceItemDecoration(private val leftRight: Int, private val topBottom: Int, private val firstNeedTop: Boolean = true) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as LinearLayoutManager?
        //竖直方向的
        if (layoutManager!!.orientation == LinearLayoutManager.VERTICAL) {
            //最后一项需要 bottom
            if (parent.getChildAdapterPosition(view) == layoutManager.itemCount - 1) {
                outRect.bottom = topBottom
            }
            if (!firstNeedTop && parent.getChildAdapterPosition(view) == 0) {
                outRect.top = 0
            } else {
                outRect.top = topBottom
            }
            outRect.left = leftRight
            outRect.right = leftRight
        } else {
            //最后一项需要right
            if (parent.getChildAdapterPosition(view) != layoutManager.itemCount - 1) {
                outRect.right = leftRight
            }
            outRect.top = topBottom
            outRect.left = 0
            outRect.bottom = topBottom
        }
    }


}

