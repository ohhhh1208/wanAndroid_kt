package com.ouo.wan_android_kt

import android.app.Application
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import com.ouo.core.Play
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Application
 *
 * Created by oxq on 2023/12/21.
 */
//@HiltAndroidApp 是 Hilt 库中的一个注解，用于标记 Android 应用程序类，并将其标识为 Hilt 应用程序。
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Play.initialize(applicationContext)
        initData()
    }

    private fun initData() {
        //CoroutineScope 是用来创建和启动协程的。
        //在一个使用 IO 调度器和 SupervisorJob 的协程作用域中启动一个新的协程。您可以在 launch 的协程代码块中编写具体的协程逻辑
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                initBugLy()
        }
    }

    private fun initBugLy() {
        // Bugly bug上报
        CrashReport.initCrashReport(applicationContext, BUGLY_APP_ID, false)
    }

    companion object {
        //static 代码段可以防止内存泄露
        init { //设置全局的Header构建器
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(
                    R.color.refresh,
                    R.color.text_color
                )//全局设置主题颜色  CustomRefreshHeader   ClassicsHeader
                ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }
}