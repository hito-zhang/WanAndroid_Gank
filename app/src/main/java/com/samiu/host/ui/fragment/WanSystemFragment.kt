package com.samiu.host.ui.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.samiu.base.ui.BaseFragment
import com.samiu.base.ui.viewBinding
import com.samiu.host.R
import com.samiu.host.databinding.FragmentWanSystemBinding
import com.samiu.host.global.toRecycler
import com.samiu.host.ui.adapter.WanSystemAdapter
import com.samiu.host.viewmodel.WanSystemViewModel
import kotlinx.android.synthetic.main.fragment_wan_system.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Samiu 2020/3/2
 */
class WanSystemFragment : BaseFragment(R.layout.fragment_wan_system) {
    private val systemViewModel: WanSystemViewModel by viewModel()
    private val binding by viewBinding (FragmentWanSystemBinding::bind)

    override fun initView() = initRecyclerView()
    override fun initData() = systemViewModel.getSystem()

    private lateinit var adapter: WanSystemAdapter

    override fun startObserve() = systemViewModel.run {
        mSystems.observe(this@WanSystemFragment, Observer {
            adapter.addAll(it)
        })
    }

    private fun initRecyclerView() {
        adapter = WanSystemAdapter(context)
        system_rv.layoutManager = LinearLayoutManager(context)
        system_rv.adapter = adapter
        adapter.setOnItemClick { cid,title -> toRecycler(this, cid,title) }
    }
}