package com.ouo.wan_android_kt.base

import android.util.Log
import androidx.lifecycle.liveData
import com.ouo.model.model.BaseModel

/**
 * Created by oxq on 2024/1/10.
 */


private const val TAG = "LiveDataUtils"

//liveDataModel接受一个挂起函数 block 作为参数，并返回一个 LiveData 对象
fun <T> liveDataModel(block: suspend () -> BaseModel<T>) =
    liveData {
        val result = try {
            val baseModel = block()
            if (baseModel.errorCode == 0) {
                // 请求成功，处理返回的数据 model
                val model = baseModel.data
                Result.success(model)
            } else {
                // 请求失败，处理错误信息 error
                Log.e(
                    TAG,
                    "fires: response status is ${baseModel.errorCode}  msg is ${baseModel.errorMsg}"
                )
                Result.failure(RuntimeException(baseModel.errorMsg))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            Result.failure(e)
        }
        emit(result)
    }

fun <T> liveDataFire(block: suspend () -> Result<T>) =
    liveData {
        val result = try {
            block()
        } catch (e: Exception) {
            Log.e(TAG, "fire $e")
            Result.failure(e)
        }
        emit(result)
    }
