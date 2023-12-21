package com.ouo.core.view.base

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.ouo.core.R
import com.ouo.core.util.dp2px
import com.ouo.core.util.showToast
import com.ouo.core.util.transparentStatusBar
import com.ouo.core.view.base.lce.DefaultLceImpl
import com.ouo.core.view.base.lce.ILce
import java.lang.ref.WeakReference

/**
 * Created by oxq on 2023/12/21.
 *
 * 应用程序中所有Activity的基类。
 *
 */

abstract class BaseActivity : AppCompatActivity(), ILce, BaseActivityInit {

    /**
     * Activity中显示加载等待的控件。
     */
    private var loading: View? = null

    /**
     * Activity中由于服务器异常导致加载失败显示的布局。
     */
    private var loadErrorView: View? = null

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private var badNetworkView: View? = null

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private var noContentView: View? = null

    private var defaultLce: ILce? = null

    private var weakRefActivity: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContentView(getLayoutView())
        ActivityCollector.add(WeakReference(this))
        weakRefActivity = WeakReference(this)
        initView()
        initData()
    }

    override fun initData() {}

    override fun setContentView(view: View?) {
        super.setContentView(view)
        setupViews()
    }

    //open 关键字用于修饰类、方法或属性，表示它们可以被继承或者重写。
    protected open fun setupViews() {
        val view = View.inflate(this, R.layout.layout_lce, null)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(
            0,
            dp2px(if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT || isSearchPage()) 70f else 55f),
            0,
            0
        )
        addContentView(view, params)
        loading = view.findViewById(R.id.loading)
        noContentView = view.findViewById(R.id.noContentView)
        badNetworkView = view.findViewById(R.id.badNetworkView)
        loadErrorView = view.findViewById(R.id.loadErrorView)
        if (loading == null) {
            Log.e(TAG, "loading is null")
        }
        if (badNetworkView == null) {
            Log.e(TAG, "badNetworkView is null")
        }
        if (loadErrorView == null) {
            Log.e(TAG, "loadErrorView is null")
        }
        defaultLce = DefaultLceImpl(
            loading,
            loadErrorView,
            badNetworkView,
            noContentView
        )
        loadFinished()
    }

    protected open fun isSearchPage(): Boolean {
        return false
    }

    /**
     * 设置 LiveData 的状态，根据不同状态显示不同页面
     *
     * @param dataLiveData LiveData
     * @param onDataStatus 数据回调进行使用
     */
    fun <T> setDataStatus(dataLiveData: LiveData<Result<T>>, onDataStatus: (T) -> Unit) {
        dataLiveData.observe(this) {
            if (it.isSuccess) {
                val dataList = it.getOrNull()
                if (dataList != null) {
                    loadFinished()
                    onDataStatus(dataList)
                } else {
                    showLoadErrorView()
                }
            } else {
                showToast(getString(R.string.bad_network_view_tip))
                showBadNetworkView { initData() }
            }
        }
    }

    //@CallSuper 是一个注解（annotation），用于指示子类在重写父类方法时，应该调用父类的实现。
// 它是 Kotlin 中的一个元注解，可以应用于方法或属性的覆盖（override）。
//
//当一个方法被标记为 @CallSuper 时，子类在重写该方法时必须调用父类的实现，以确保父类的逻辑得以执行。
// 如果子类没有调用父类的方法，编译器会发出警告。
    @CallSuper
    override fun startLoading() {
        defaultLce?.startLoading()
    }

    @CallSuper
    override fun loadFinished() {
        defaultLce?.loadFinished()
    }

    override fun showLoadErrorView(tip: String) {
        defaultLce?.showLoadErrorView(tip)
    }

    override fun showBadNetworkView(listener: View.OnClickListener) {
        defaultLce?.showBadNetworkView(listener)
    }

    override fun showNoContentView(tip: String) {
        defaultLce?.showNoContentView(tip)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.remove(weakRefActivity)
    }


    //companion 关键字用于定义一个伴生对象。伴生对象是一个在类内部定义的对象，可以访问类的私有成员，就像是类的静态成员一样。
    //伴生对象可以访问类的私有成员，而且可以通过类名直接访问，就像调用静态方法一样。它们在类加载时就会初始化，且只会有一个实例。
    companion object {
        private const val TAG = "BaseActivity"
    }
}