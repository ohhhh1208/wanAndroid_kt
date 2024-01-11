package com.ouo.wan_android_kt.main.login

import com.ouo.network.base.PlayAndroidNetwork
import com.ouo.wan_android_kt.base.liveDataModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * Created by oxq on 2024/1/10.
 */

//@ActivityRetainedScoped 是 Dagger Hilt 中的一个注解，
// 用于标记在 Android 中保留状态的组件或依赖注入对象。
@ActivityRetainedScoped
class AccountRepository @Inject constructor() {

    suspend fun getLogin(username: String, password: String) =
        PlayAndroidNetwork.getLogin(username, password)

    suspend fun getRegister(username: String, password: String, repassword: String) =
        PlayAndroidNetwork.getRegister(username, password, repassword)

    fun getLogout() = liveDataModel{
        PlayAndroidNetwork.getLogout()
    }
}