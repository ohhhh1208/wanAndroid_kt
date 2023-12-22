package com.ouo.wan_android_kt

import android.view.View
import android.view.View.OnClickListener
import com.ouo.core.view.base.BaseActivity
import com.ouo.wan_android_kt.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity() ,
    OnClickListener{
    private lateinit var binding: ActivityWelcomeBinding


    override fun getLayoutView(): View {
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.ivWelcomeBg.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

    }
}