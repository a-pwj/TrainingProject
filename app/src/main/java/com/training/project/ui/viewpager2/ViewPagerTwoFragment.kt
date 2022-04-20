package com.training.project.ui.viewpager2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.training.project.R
import com.training.project.ui.viewpager2.viewmodel.ViewPager2ViewModel

/**
 * @Author:          pwj
 * @Date:            2022/1/26 14:50
 * @FileName:        ViewPagerOneFragment.kt
 * @Description:     simple viewpager2 two
 */
class ViewPagerTwoFragment : Fragment() {
    private val TAG = "ViewPagerFragment"
    private var param1: Int? = -1
    private val viewModel: ViewPager2ViewModel by activityViewModels()


    private val fragments = mutableListOf<Fragment>().apply {
        add(ViewPagerThreeFragment.newInstance(111))
        add(ViewPagerThreeFragment.newInstance(222))
        add(ViewPagerThreeFragment.newInstance(333))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        Log.d(TAG,"onCreate=============${param1}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_pager_two, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.tvTag)
        textView.text = "这是第${param1}个页面"

        view.findViewById<Button>(R.id.add).setOnClickListener {
            viewModel.emitValue(true)
        }


        val viewPager2 = view.findViewById<ViewPager2>(R.id.view_pager2)
        viewPager2.isSaveEnabled=false
        viewPager2.adapter = ViewPagerOneFragment.ViewPagerAdapter(this, fragments)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                textView.text = "这是第${param1}个页面,onPageSelected =${position}"
            }
        })
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
            ViewPagerTwoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}