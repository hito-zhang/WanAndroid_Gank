package com.samiu.host.ui.fragment.wan

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.jeremyliao.liveeventbus.LiveEventBus
import com.samiu.base.interactive.ZoomOutPageTransformer
import com.samiu.base.ui.BaseFragment
import com.samiu.host.R
import kotlinx.android.synthetic.main.fragment_wan_nav.*

/**
 * 玩安卓 底部导航栏的首页
 * @author Samiu 2020/3/2
 */
class WanNavFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_wan_nav
    override fun initData() = Unit

    private val homeFragment by lazy { WanHomeFragment() }
    private val squareFragment by lazy { WanSquareFragment() }
    private val articleFragment by lazy { WanArticleFragment() }
    private val systemFragment by lazy { WanSystemFragment() }
    private val navigationFragment by lazy { WanNavigationFragment() }
    private val fragmentList = ArrayList<Fragment>()
    private val titleList = arrayOf("首页", "广场", "公众号", "体系", "导航")
    private var currentTitle = titleList[0]

    init {
        fragmentList.add(homeFragment)
        fragmentList.add(squareFragment)
        fragmentList.add(articleFragment)
        fragmentList.add(systemFragment)
        fragmentList.add(navigationFragment)
    }

    override fun initView(){
        //viewPager2
        pager.adapter = ScreenPagerAdapter(this)
        pager.setPageTransformer(ZoomOutPageTransformer())
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentTitle = titleList[position]
            }
        })
        //tabLayout
        TabLayoutMediator(tab,pager){tab, position ->  tab.text = titleList[position]}.attach()
        //smartRefreshLayout
        homeRefreshLayout.setOnRefreshListener {
            LiveEventBus
                .get(currentTitle,Int::class.java)
                .post(-1)
            it.finishRefresh(1500)
        }
        homeRefreshLayout.setOnLoadMoreListener {
            LiveEventBus
                .get(currentTitle,Int::class.java)
                .post(1)
            it.finishLoadMore(1500)
        }
    }

    private inner class ScreenPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = fragmentList.size
        override fun createFragment(position: Int) = fragmentList[position]
    }
}