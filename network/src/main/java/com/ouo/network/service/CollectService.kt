package com.ouo.network.service

import com.ouo.model.model.BaseModel
import com.ouo.model.model.Collect
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by oxq on 2023/12/19.
 */
interface CollectService {

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): BaseModel<Collect>

    @POST("lg/collect/{id}/json")
    suspend fun toCollect(@Path("id") id: Int): BaseModel<Any>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") id: Int): BaseModel<Any>

}

//suspend的字面含义是暂停、挂起的意思。在kotlin中，代码执行到 suspend 函数的时候会『挂起』，
// 并且这个『挂起』是非阻塞式的，它不会阻塞你当前的线程，
// 挂起的定位: 暂时切走，稍后再切回来，和java的Thread.sleep()不一样，一个是阻塞，一个是等待，类似wait。


//如果当前方法被suspend修饰了，如果不是通过runblock或者coroutines 库调用，那么调用链上的所有函数都要背suspend修饰