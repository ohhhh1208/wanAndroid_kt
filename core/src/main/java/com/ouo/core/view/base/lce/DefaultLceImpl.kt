package com.ouo.core.view.base.lce

import android.view.View
import android.widget.TextView
import com.ouo.core.R

/**
 * Created by oxq on 2023/12/21.
 *
 * 具体的策略
 * @author zhujiang
 *
 */
class DefaultLceImpl constructor(
//    在 Kotlin 中，constructor 关键字用于定义类的构造函数，即用于创建类的实例的特殊方法。
//    构造函数用于初始化类的属性和执行一些必要的设置操作。
//    主构造函数：主构造函数是类头的一部分，直接在类名后面声明。它可以带有参数，这些参数可以在类的初始化过程中使用。
//    次构造函数：次构造函数是在类体内部定义的附加构造函数。它们的声明以 constructor 关键字开头，并可以有自己的参数。
    private val loading: View?,
    private val loadErrorView: View?,
    private val badNetworkView: View?,
    private val noContentView: View?
) : ILce {

    override fun startLoading() {
        loadFinished()
        loading?.visibility = View.VISIBLE
    }

    override fun loadFinished() {
        loading?.visibility = View.GONE
        badNetworkView?.visibility = View.GONE
        noContentView?.visibility = View.GONE
        loadErrorView?.visibility = View.GONE
    }

    override fun showLoadErrorView(tip: String) {
        loadFinished()
        val loadErrorText = loadErrorView?.findViewById<TextView>(R.id.loadErrorText)
        loadErrorText?.text = tip
        loadErrorView?.visibility = View.VISIBLE
    }

    override fun showBadNetworkView(listener: View.OnClickListener) {
        loadFinished()
        badNetworkView?.visibility = View.VISIBLE
        badNetworkView?.setOnClickListener(listener)
    }

    override fun showNoContentView(tip: String) {
        loadFinished()
        val noContentView = noContentView?.findViewById<TextView>(R.id.noContentView)
        noContentView?.text = tip
        noContentView?.visibility = View.VISIBLE
    }

}