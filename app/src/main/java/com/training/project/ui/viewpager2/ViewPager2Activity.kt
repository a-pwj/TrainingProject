package com.training.project.ui.viewpager2

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.training.project.R
import com.training.project.base.BaseVBActivity
import com.training.project.databinding.ActivityViewPager2Binding
import com.training.project.ui.viewpager2.viewmodel.ViewPager2ViewModel

class ViewPager2Activity : BaseVBActivity<ActivityViewPager2Binding>() {

    private val viewModel:ViewPager2ViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        replaceFragment(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        replaceFragment(intent)
    }

    private fun replaceFragment(intent: Intent?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, ViewPagerOneFragment.newInstance(1))
            .commit()
    }
}