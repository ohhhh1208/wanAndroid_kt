package com.ouo.wan_android_kt.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by oxq on 2023/12/25.
 */
class MainViewModel : ViewModel() {

    private val pageLiveData = MutableLiveData<Int>()

    fun setPage(page: Int) {
        pageLiveData.value = page
    }

    fun getPage(): Int? {
        return pageLiveData.value
    }
}