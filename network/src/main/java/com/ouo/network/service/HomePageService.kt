package com.ouo.network.service

import com.ouo.model.model.ArticleList
import com.ouo.model.model.BaseModel
import com.ouo.model.room.entity.Article
import com.ouo.model.room.entity.BannerBean
import com.ouo.model.room.entity.HotKey
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by oxq on 2023/12/20.
 */
interface HomePageService {
    @GET("banner/json")
    suspend fun getBanner(): BaseModel<List<BannerBean>>

    @GET("article/top/json")
    suspend fun getTopArticle(): BaseModel<List<Article>>

    @GET("article/list/{a}/json")
    suspend fun getArticle(@Path("a") a: Int): BaseModel<ArticleList>

    @GET("hotkey/json")
    suspend fun getHotKey(): BaseModel<List<HotKey>>

    @POST("article/query/{page}/json")
    suspend fun getQueryArticleList(
        @Path("page") page: Int,
        @Query("k") k: String
    ): BaseModel<ArticleList>

}