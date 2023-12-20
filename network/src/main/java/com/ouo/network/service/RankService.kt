package com.ouo.network.service

import com.ouo.model.model.BaseModel
import com.ouo.model.model.RankData
import com.ouo.model.model.RankList
import com.ouo.model.model.UserInfo
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by oxq on 2023/12/20.
 */
interface RankService {
    @GET("coin/rank/{page}/json")
    suspend fun getRankList(@Path("page") page: Int): BaseModel<RankData>

    @GET("lg/coin/userinfo/json")
    suspend fun getUserInfo(): BaseModel<UserInfo>

    @GET("lg/coin/list/{page}/json")
    suspend fun getUserRank(@Path("page") page: Int): BaseModel<RankList>

}