package com.ouo.network.service

import com.ouo.model.model.ArticleList
import com.ouo.model.model.BaseModel
import com.ouo.model.room.entity.ProjectClassify
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by oxq on 2023/12/20.
 */
interface ProjectService {
    @GET("project/tree/json")
    suspend fun getProjectTree(): BaseModel<List<ProjectClassify>>

    @GET("project/list/{page}/json")
    suspend fun getProject(@Path("page") page: Int, @Query("cid") cid: Int): BaseModel<ArticleList>

}