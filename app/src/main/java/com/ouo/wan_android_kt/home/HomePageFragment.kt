package com.ouo.wan_android_kt.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ouo.wan_android_kt.databinding.FragmentHomePageBinding

class HomePageFragment : ArticleCollectBaseFragment() {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    private val viewModel by viewModels<HomePageViewModel>()
    private var binding: FragmentHomePageBinding? = null

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater, container, attachToRoot)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun refreshData() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

}