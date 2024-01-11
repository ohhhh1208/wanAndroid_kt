package com.ouo.wan_android_kt.base

import android.util.Log
import com.ouo.model.model.BaseModel
import kotlinx.coroutines.*

/**
 * Created by oxq on 2024/1/11.
 *
 * create by Rui on 2022/8/30
 * desc: 协程http 请求扩展
 */


private const val TAG = "ScopeExt"

/**
 * 扩展函数 http，发起网络请求并处理响应结果
 * 当前协程 未指定 调度线程，恢复挂起的数据仍在 当前线程 中
 */
fun <T> CoroutineScope.http(
    //request接受一个无参的挂起函数作为输入，并返回 BaseModel<T> 类型的结果。用于发送网络请求并获取响应数据
    request: (suspend () -> BaseModel<T>),

    //response接受一个泛型类型为 T 的参数，并没有返回值。用于处理网络请求成功时得到的数据
    response: (T?) -> Unit,

    //error接受一个 String 类型的参数，并没有返回值。用于处理网络请求失败时的错误信息
    error: (String) -> Unit = {},
    showToast: Boolean = true
): Job {
    //一个 Job 对象是一个代表正在执行的异步操作的任务，可以用它来控制与取消协程的执行
    //使用协程作用域的 launch 函数开启一个新的协程。在这个协程中，它尝试执行 request 挂起函数，并捕获可能抛出的异常
    return this.launch() {
        try {
            val result = request()
            if (result.errorCode == 0) {
                response(result.data)
            } else {
                showToast(showToast, result.errorMsg)
                error(result.errorMsg)
            }
        } catch (e: Exception) {
            showToast(showToast, e.message)
            error(e.message ?: "异常")
        }
    }
}

/**
 * 当前协程 指定 调度线程，恢复挂起的数据仍在当前 指定的线程 中
 */
fun <T> CoroutineScope.http2(
    request: (suspend () -> BaseModel<T>),
    response: (T?) -> Unit,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    error: (String) -> Unit = {},
    showToast: Boolean = true

): Job {
    return this.launch(dispatcher) {
        try {
            val result = request()
            if (result.errorCode == 0) {
                response(result.data)
            } else {
                showToast(showToast, result.errorMsg)
                error(result.errorMsg)
            }
        } catch (e: Exception) {
            showToast(showToast, e.message)
            error(e.message ?: "异常")
        }

    }
}

private fun showToast(isShow: Boolean, msg: String?) {
    Log.e(TAG, "showToast: isShow:$isShow   msg:$msg")
}