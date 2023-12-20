package com.ouo.network.service

import com.ouo.model.model.BaseModel
import com.ouo.model.model.Login
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by oxq on 2023/12/20.
 */
interface LoginService {

    @POST("user/login")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): BaseModel<Login>

    @POST("user/register")
    suspend fun getRegister(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): BaseModel<Login>

    @GET("user/logout/json")
    suspend fun getLogout(): BaseModel<Any>
}