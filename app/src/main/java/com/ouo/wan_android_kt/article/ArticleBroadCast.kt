package com.ouo.wan_android_kt.article

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/**
 * Created by oxq on 2024/1/9.
 *
 * 版权：Zhujiang 个人版权
 *
 * @author zhujiang
 * 创建日期：2020/9/14
 * 描述：PlayAndroid
 *
 * 封装了发送和接收文章变化广播的逻辑
 */


//object 关键字用于创建单例对象，在该单例对象中定义的方法可以被视为静态方法的等效物，
// 它们可以在不创建对象实例的情况下直接通过单例对象进行访问。
object ArticleBroadCast {

    const val COLLECT_RECEIVER = "com.ouo.wan_android_kt.COLLECT"

    //静态方法，用于发送文章变化的广播
    fun sendArticleChangeReceiver(context: Context) {
        val intent = Intent(COLLECT_RECEIVER)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    //静态方法，用于注册接收文章变化广播的 BroadcastReceiver
    //接受一个 Activity 参数和一个带有无参返回值的 lambda 表达式 block，表示当接收到广播时要执行的操作
    fun setArticleChangeReceiver(c: Activity, block: () -> Unit): BroadcastReceiver {
        val filter = IntentFilter()
        filter.addAction(COLLECT_RECEIVER)
        val r = ArticleBroadcastReceiver(block)
        LocalBroadcastManager.getInstance(c).registerReceiver(r, filter)
        return r
    }

    //静态方法，用于取消注册文章变化广播的 BroadcastReceiver
    fun clearArticleChangeReceiver(c: Activity, r: BroadcastReceiver?) {
        //apply 是一个标准函数，apply 函数通常用于在对象创建和初始化过程中使用，
        // 它允许你在对象上执行一系列操作，并返回该对象本身
        r?.apply {
            LocalBroadcastManager.getInstance(c).unregisterReceiver(this)
        }
    }

}

//处理接收到的广播，并在广播动作为 ArticleBroadCast.COLLECT_RECEIVER 时执行传入的代码块。
// 通过使用这个内部类，可以将特定的操作封装在广播接收器中，以便在接收到特定广播时执行相关的逻辑。
private class ArticleBroadcastReceiver(val block: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("TAG", "onReceive:${intent.action}")
        if (intent.action == ArticleBroadCast.COLLECT_RECEIVER) {
            //block.invoke() 的作用是执行 block 这个 Lambda 表达式，并返回其结果（如果有的话）
            block.invoke()
        }
    }
}