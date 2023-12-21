package com.ouo.core.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

/**
 * Created by oxq on 2023/12/21.
 */
abstract class BaseAndroidViewModel<BaseData, Data, Key> : ViewModel() {

    val dataList = ArrayList<Data>()

    private val pageLiveData = MutableLiveData<Key>()

    val dataLiveData = pageLiveData.switchMap { page ->
        getData(page)
    }

    abstract fun getData(page: Key): LiveData<Result<BaseData>>

    fun getDataList(page: Key) {
        pageLiveData.value = page!!
    }

}