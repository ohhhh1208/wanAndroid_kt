package com.ouo.core

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.ouo.core.util.DataStoreUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by oxq on 2023/12/21.
 *
 * 全局的API接口。
 *
 */

//@SuppressLint("StaticFieldLeak") 注解的使用可以帮助您消除由于静态字段泄漏警告机制引起的警告,
//在某些情况下，您可能会故意在非静态的内部类中持有静态的引用，例如使用静态的 Context 或者全局的单例对象。
@SuppressLint("StaticFieldLeak")
object Play {
    private const val TAG = "Play"
    private const val USERNAME = "username"
    private const val NICK_NAME = "nickname"
    private const val IS_LOGIN = "isLogin"

    //lateinit：是一个修饰符，用于延迟初始化属性
    //需要确保在使用属性之前进行初始化，否则会抛出 UninitializedPropertyAccessException 异常。
    private lateinit var dataStore: DataStoreUtils

    /**
     * 获取全局Context，在代码的任意位置都可以调用，随时都能获取到全局Context对象。
     *
     * @return 全局Context对象。
     */
    var context: Context? = null
        //private set 是一个访问修饰符，用于指定该变量在类的外部只能被读取，不能被修改
        private set

    /**
     * 初始化接口。这里会进行应用程序的初始化操作，一定要在代码执行的最开始调用。
     *
     * @param c Context参数，注意这里要传入的是Application的Context，千万不能传入Activity或者Service的Context。
     */
    fun initialize(c: Context?) {
        if (c == null) {
            Log.w(TAG, "initialize: context is null")
            return
        }
        context = c
        //在括号内部，apply 函数将当前的上下文（context）作为接收者对象（this）传递给代码块。
        // 这意味着您可以在代码块内直接使用 this 来引用 context 对象。
        context?.apply {
            dataStore = DataStoreUtils.init(this)
        }
    }

    /**
     * 判断用户是否已登录。
     *
     * @return 已登录返回true，未登录返回false。
     */
    fun isLogin(): Flow<Boolean> {
        return if (::dataStore.isInitialized) {
            dataStore.readBooleanFlow(IS_LOGIN)
        } else {
            // Flow 是 Kotlin 提供的用于异步数据流的库，它支持异步数据的发射和收集。
            // 它类似于 RxJava 中的 Observable，是一种用于处理连续流数据的声明性编程方式。
            //Flow 可以处理潜在的无限数据源，且它是基于协程的，因此可以方便地与挂起函数一起使用。
            //Flow 可以通过 emit() 函数发射值
            flow { emit(false) }
        }
    }

    fun isLoginResult(): Boolean {
        return if (::dataStore.isInitialized) {
            dataStore.readBooleanData(IS_LOGIN)
        } else {
            false
        }
    }

    fun setLogin(isLogin: Boolean) {
        dataStore.saveSyncBooleanData(IS_LOGIN, isLogin)
    }

    /**
     * 注销用户登录。
     */
    fun logout() {
        dataStore.clearSync()
    }

    fun setUserInfo(nickname: String, username: String) {
        dataStore.saveSyncStringData(NICK_NAME, nickname)
        dataStore.saveSyncStringData(USERNAME, username)
    }

    //get() 函数使用 dataStore.readStringData(NICK_NAME) 方法来获取昵称数据，并将其作为属性的值返回。
    // 这意味着每当我们访问 nickname 属性时，它会动态地从 dataStore 中读取最新的昵称数据。
    val nickname: String
        get() = dataStore.readStringData(NICK_NAME)
    val username: String
        get() = dataStore.readStringData(USERNAME)
}