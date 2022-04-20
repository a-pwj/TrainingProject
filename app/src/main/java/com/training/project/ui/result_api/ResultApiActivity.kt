package com.training.project.ui.result_api

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityResultApiBinding

class ResultApiActivity : BaseVBActivity<ActivityResultApiBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.viewPager2.adapter = ViewPagerAdapter(this)
        mDataBinding.viewPager2.offscreenPageLimit = 1
    }

    class ViewPagerAdapter(activity: ResultApiActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return PageFragment.newInstance(position)
        }
    }

}