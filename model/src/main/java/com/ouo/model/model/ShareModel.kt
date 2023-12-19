package com.ouo.model.model

import com.ouo.model.room.entity.Article

/**
 * Created by oxq on 2023/12/19.
 */

data class ShareModel(
    val coinInfo: CoinInfo,
    val shareArticles: ShareArticles
)

data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val rank: String,
    val userId: Int,
    val username: String
)

data class ShareArticles(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)