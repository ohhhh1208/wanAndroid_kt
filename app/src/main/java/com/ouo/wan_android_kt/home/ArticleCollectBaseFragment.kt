package com.ouo.wan_android_kt.home

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.View
import com.ouo.core.view.base.BaseFragment
import com.ouo.wan_android_kt.article.ArticleBroadCast

/**
 * Created by oxq on 2024/1/9.
 *
 * 版权：Zhujiang 个人版权
 *
 * @author zhujiang
 * 创建日期：2020/9/15
 * 描述：文章收藏 BaseFragment，注册文章收藏状态改变的广播
 *
 */

abstract class ArticleCollectBaseFragment : BaseFragment() {

    private var articleReceiver: BroadcastReceiver? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleReceiver = ArticleBroadCast.setArticleChangeReceiver(
            requireActivity()
        ) {
            refreshData()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onDestroy() {
        super.onDestroy()
        ArticleBroadCast.clearArticleChangeReceiver(requireActivity(), articleReceiver)
    }


    abstract fun refreshData()

}