package com.training.project.ui.viewpager2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.training.project.R

/**
 * @Author:          pwj
 * @Date:            2022/1/26 14:50
 * @FileName:        ViewPagerOneFragment.kt
 * @Description:     simple viewpager2 two
 */
class ViewPagerThreeFragment : Fragment() {
    private val TAG = "ViewPagerFragment"
    private var param1: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        Log.d(TAG,"onCreate=============${param1}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_pager_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.tvTag)
        textView.text = "这是第${param1}个页面"
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart=============${param1}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume=============${param1}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause=============${param1}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop=============${param1}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy=============${param1}")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            ViewPagerThreeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}