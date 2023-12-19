package com.ouo.model.model

import com.ouo.model.room.entity.Article

/**
 * Created by oxq on 2023/12/19.
 */

data class ArticleList(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
