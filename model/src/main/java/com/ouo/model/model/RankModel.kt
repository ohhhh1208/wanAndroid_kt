package com.ouo.model.model

/**
 * Created by oxq on 2023/12/19.
 */


data class RankData(
    val curPage: Int,
    val datas: List<Rank>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Rank(
    val coinCount: Int,
    val level: Int,
    val rank: String,
    val userId: Int,
    val username: String
)
