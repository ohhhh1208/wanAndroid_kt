package com.ouo.network.service

import com.ouo.model.model.ArticleList
import com.ouo.model.model.BaseModel
import com.ouo.model.room.entity.ProjectClassify
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by oxq on 2023/12/20.
 */
interface OfficialService {
    @GET("wxarticle/chapters/json")
    suspend fun getWxArticleTree(): BaseModel<List<ProjectClassify>>

    @GET("wxarticle/list/{cid}/{page}/json")
    suspend fun getWxArticle(@Path("page") page: Int, @Path("cid") cid: Int): BaseModel<ArticleList>

}