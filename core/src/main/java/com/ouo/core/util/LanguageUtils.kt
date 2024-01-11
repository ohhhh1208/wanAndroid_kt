package com.ouo.core.util

import java.util.*

/**
 * Created by oxq on 2024/1/11.
 */

fun isZhLanguage(): Boolean {
    return Locale.getDefault().language == "zh"
}