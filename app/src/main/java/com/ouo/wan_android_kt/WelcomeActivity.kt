package com.ouo.wan_android_kt

import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import com.ouo.core.util.showToast
import com.ouo.core.view.base.BaseActivity
import com.ouo.wan_android_kt.databinding.ActivityWelcomeBinding
import com.ouo.wan_android_kt.main.MainActivity

class WelcomeActivity : BaseActivity(),
    OnClickListener {
    private lateinit var binding: ActivityWelcomeBinding
    private var exitTime: Long = 0
    private val animationTime: Long = 500


    override fun getLayoutView(): View {
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
//        SetTextI18n 是一个警告，它建议开发者应该避免在代码中直接设置文本内容，而是使用字符串资源进行国际化。
        initAnimation()
        binding.ivWelcomeBg.setOnClickListener(this)
    }

    private fun initAnimation() {

//      创建旋转动画效果  RotateAnimation 的参数设置为从 0 度旋转到 360 度，中心点位于 View 的中心。
        val rotateAnimation =
            RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
        rotateAnimation.duration = animationTime

//        当动画播放完毕时，被应用动画的对象将停留在最后一帧的状态
        rotateAnimation.fillAfter = true

//      创建缩放动画效果  设置为从 0 倍缩放比例缩放到 1 倍，中心点位于 View 的中心
        val scaleAnimation = ScaleAnimation(
            0f, 1f, 0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = animationTime
        scaleAnimation.fillAfter = true

//        创建透明度动画效果 设置为从完全透明（透明度为 0）淡入到完全不透明（透明度为 1）
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = animationTime
        alphaAnimation.fillAfter = true

//        AnimationSet 是 Android 中的一个动画类，用于组合多个动画效果。
//        通过将多个动画添加到 AnimationSet 中，可以同时播放这些动画，从而实现更复杂的动画效果。
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)

        binding.ivWelcomeBg.startAnimation(animationSet)
        animationSet.setAnimationListener(animationListener)

    }

    private val animationListener = object : Animation.AnimationListener {
        /**
         * 动画开始的时候执行
         * @param p0
         */
        override fun onAnimationStart(p0: Animation?) {
        }

        /**
         * 动画结束的时候执行
         * @param p0
         */
        override fun onAnimationEnd(p0: Animation?) {
            //跳转到登陆界面
            jump()
        }

        /**
         * 动画重复的时候执行
         * @param p0
         */
        override fun onAnimationRepeat(p0: Animation?) {
        }
    }

    private fun jump() {
        MainActivity.actionStart(this)
        finish()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivWelcomeBg -> {
                jump()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exit() {
//        2秒连续返回退出
        if (System.currentTimeMillis() - exitTime > 2000) {
            showToast(R.string.exit_program)
            exitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }
}