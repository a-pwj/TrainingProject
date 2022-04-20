package com.training.project.ui.viewpager2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.training.project.R
import com.training.project.ui.viewpager2.viewmodel.ViewPager2ViewModel
import kotlinx.coroutines.flow.collect


const val ARG_PARAM1 = "param1"
const val RESULT_KEY = "result_key"
const val RESULT_BUNDLE_KEY = "result_bundle_key"

/**
 * @Author:          pwj
 * @Date:            2022/1/26 14:50
 * @FileName:        ViewPagerOneFragment.kt
 * @Description:     simple viewpager2 one
 */
class ViewPagerOneFragment : Fragment() {
    private var param1: Int? = -1

    private val viewModel: ViewPager2ViewModel by activityViewModels()
    private val fragments = mutableListOf<Fragment>().apply {
        add(ViewPagerTwoFragment.newInstance(1))
        add(ViewPagerTwoFragment.newInstance(2))
        add(ViewPagerTwoFragment.newInstance(3))
    }

    private var adapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_pager_one, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.tvTag)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.view_pager2)
        textView.text = "这是第${param1}个页面"

        adapter = ViewPagerAdapter(this, fragments)
        viewPager2.adapter = adapter
        (viewPager2.getChildAt(0) as RecyclerView).layoutManager?.isItemPrefetchEnabled = false
        (viewPager2.getChildAt(0) as RecyclerView).setItemViewCacheSize(0)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                textView.text = "这是第${param1}个页面,onPageSelected =${position}"
            }
        })

        lifecycleScope.launchWhenCreated {
            viewModel.getSharedFlow().collect {
                adapter?.let { addFragment(ViewPagerTwoFragment.newInstance(it.itemCount + 1)) }
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        adapter?.addFragment(fragment)
    }

    class ViewPagerAdapter(fragment: Fragment, val fragments: MutableList<Fragment>) : FragmentStateAdapter(fragment) {
        private val mFragmentHashCodes = mutableListOf<Long>()

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun getItemId(position: Int): Long {
            return fragments[position].hashCode().toLong()
        }

//        override fun containsItem(itemId: Long): Boolean {
//            return fragments.contains(itemId)
//        }

        fun addFragment(fragment: Fragment) {
            if (!fragments.contains(fragment)) {
                fragments.add(fragment)
                notifyItemInserted(fragments.size - 1)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            ViewPagerOneFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}