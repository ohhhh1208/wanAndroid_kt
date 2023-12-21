package com.ouo.core.util

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.fragment.app.Fragment

/**
 * Created by oxq on 2023/12/21.
 */


//在 Kotlin 中，函数不一定需要定义在类中。Kotlin 支持顶层函数（Top-level functions），
// 这意味着您可以在文件的顶层定义函数，而不必将其放在类中。
//顶层函数是直接在文件中定义的函数，没有包含在任何类或对象中。
// 这使得您可以更直接地组织您的代码，使代码更加清晰和模块化。

/**
 * 设置透明状态栏
 */
fun Activity.transparentStatusBar() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun Context?.getStatusBarHeight(): Int {
    var result = 60
    if (this == null) return result
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resId > 0) {
        result = resources.getDimensionPixelOffset(resId)
    }
    return result
}

/**
 * 状态栏反色
 */
fun Activity.setAndroidNativeLightStatusBar(isDarkMode: Boolean) {
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.isAppearanceLightStatusBars = isDarkMode
}

fun Fragment?.showIme(currentFocusView: View? = null) {
    this?.activity?.showIme(currentFocusView)
}

fun Fragment?.hideIme(currentFocusView: View? = null) {
    this?.activity?.hideIme(currentFocusView)
}

/**
 * 显示ime
 */
fun Activity?.showIme(currentFocusView: View? = null) {
    //这段函数是一个 Kotlin 扩展函数，可以在 Activity 类型的对象上调用。
    // 函数的目的是在指定的视图上显示输入法。如果没有传入指定的视图，则会使用当前焦点的视图或窗口的根视图。
    // 函数使用 Handler 来延迟 100 毫秒后显示输入法，以确保其他的 UI 更新操作已经完成。
    if (this == null || window == null) return
    val view = currentFocusView ?: window.decorView
    view.isFocusable = true
    view.requestFocus()
    view.findFocus()
    val controller = WindowCompat.getInsetsController(window, view)
    Handler(Looper.getMainLooper()).postDelayed({
        controller.show(ime())
    }, 100L)
}

/**
 * 隐藏ime
 */
fun Activity?.hideIme(currentFocusView: View? = null) {
    if (this == null || window == null) return
    val view = currentFocusView ?: window.decorView
    view.clearFocus()
    val controller = WindowCompat.getInsetsController(window, view)
    controller.hide(ime())
}

fun Activity?.hideKeyboard(currentFocusView: View? = null) {
    if (this == null || window == null) return
    val view = currentFocusView ?: window.decorView
    prvHideKeyboard(view)
}

fun Fragment?.hideKeyboard(currentFocusView: View? = null) {
    this?.activity?.hideKeyboard(currentFocusView)
}

private fun Context.prvHideKeyboard(view: View) {
    view.clearFocus()
    //使用 InputMethodManager 将输入法键盘从指定的视图中隐藏起来。
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}