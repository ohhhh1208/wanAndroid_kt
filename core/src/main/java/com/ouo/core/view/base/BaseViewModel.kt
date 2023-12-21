package com.ouo.core.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap


/**
 * Created by oxq on 2023/12/21.
 */
abstract class BaseViewModel<BaseData, Data, Key> : ViewModel() {

    val dataList = ArrayList<Data>()

    //MutableLiveData 是一种可观察的数据持有类，用于在组件（如 Activity 或 Fragment）之间传递数据。
    // 它通常用于在应用程序的不同部分之间进行通信或共享状态。
    private val pageLiveData = MutableLiveData<Key>()

    //switchMap 会根据 pageLiveData 的值进行转换，并返回一个新的 LiveData 对象，即 dataLiveData。
    // 这个新的 LiveData 对象的值是通过调用 getData(page) 函数获得的
    val dataLiveData = pageLiveData.switchMap { page -> getData(page) }

    abstract fun getData(page: Key): LiveData<Result<BaseData>>

    //page!! 是 Kotlin 的非空断言操作符。在这里，它表示 page 变量的非空断言，即断言 page 不为null。
    // 如果 page 为null，那么这行代码会抛出 NullPointerException。
    //! 安全调用操作符： ! 安全调用操作符用于在访问一个可空对象的属性或调用其方法时，避免出现 null 引用异常。
    // 如果对象为 null，那么调用属性或方法的结果将会是 null，而不是抛出异常。
    fun getDataList(page: Key) {
        pageLiveData.value = page!!
    }
}