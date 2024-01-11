package com.ouo.wan_android_kt.main.login

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ouo.core.util.checkNetworkAvailable
import com.ouo.core.util.showToast
import com.ouo.core.view.base.BaseActivity
import com.ouo.wan_android_kt.R
import com.ouo.wan_android_kt.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint标记活动，从而使其成为 Hilt 的注入目标
@AndroidEntryPoint
class LoginActivity : BaseActivity(), View.OnClickListener, TextWatcher {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    private var mUserName = ""
    private var mPassWord = ""
    private var mIsLogin = true

    override fun getLayoutView(): View {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.loginButton.setOnClickListener(this)
        binding.loginTvRegister.setOnClickListener(this)
        binding.loginPassNumberClear.setOnClickListener(this)
        binding.loginPassNumberVisible.setOnClickListener(this)
        binding.loginPassNumberEdit.addTextChangedListener(this)

        //将 EditText 控件中输入的文本内容转换为密码形式显示
        binding.loginPassNumberEdit.transformationMethod =
            PasswordTransformationMethod.getInstance()

        //state发生变化时执行操作
        viewModel.state.observe(this) {
            when (it) {
                Logging -> {
                    // 登录中的处理
                    toProgressVisible(true)
                }

                is LoginSuccess -> {
                    // 登录成功后的处理
                    toProgressVisible(false)
                    finish()
                }
                is LoginError -> {
                    // 登录失败后的处理
                    toProgressVisible(false)
                }
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginTvRegister -> {
                flipAnimatorXViewShow(binding.loginInputElements)
            }
            R.id.loginButton -> {
                loginOrRegister()
            }
            R.id.loginPassNumberClear -> {
                binding.loginPassNumberEdit.setText("")
            }
            R.id.loginPassNumberVisible -> {
                //设置密码是否显示
                val transformationMethod = binding.loginPassNumberEdit.transformationMethod
                if (transformationMethod is PasswordTransformationMethod) {
                    binding.loginPassNumberEdit.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.loginPassNumberVisible.setColorFilter(getColor(R.color.colorLoading))
                } else {
                    binding.loginPassNumberEdit.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.loginPassNumberVisible.setColorFilter(getColor(R.color.text_color_black))
                }
                //将光标移动到最后一位
                binding.loginPassNumberEdit.setSelection(
                    binding.loginPassNumberEdit.text.toString().trim().length
                )
            }

        }
    }

    private fun toProgressVisible(visible: Boolean) {
        binding.loginProgressBar.isVisible = visible
        binding.loginInputElements.isVisible = !visible
    }

    /**
     * 实现 View 的翻转动画效果，并在动画结束后更新状态
     */
    private fun flipAnimatorXViewShow(view: View) {
        val animator1 = ObjectAnimator.ofFloat(view, "rotationY", 0f, if (mIsLogin) 90f else -90f)
        val animator2 = ObjectAnimator.ofFloat(view, "rotationY", if (mIsLogin) -90f else 90f, 0f)
        animator2.interpolator = OvershootInterpolator(2.0f)
        animator1.setDuration(700).start()
        animator1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                animator2.setDuration(700).start()
                updateState()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    /**
     * 显示注册/登录UI
     */
    private fun updateState() {
        binding.loginTvRegister.text =
            if (mIsLogin) getString(R.string.return_login) else getString(R.string.register_account)
        binding.loginButton.text =
            if (mIsLogin) getString(R.string.register_account) else getString(R.string.login)
        mIsLogin = !mIsLogin
    }

    /**
     * 校验输入数据以及网络状态
     */
    private fun judge(): Boolean {
        mUserName = binding.loginPassNumberEdit.text.toString()
        mPassWord = binding.loginPassNumberEdit.text.toString()
        if (TextUtils.isEmpty(mUserName) || mUserName.length < 6) {
            binding.loginPassNumberEdit.error = getString(R.string.enter_name_format)
            return false
        }
        if (TextUtils.isEmpty(mPassWord) || mPassWord.length < 6) {
            binding.loginPassNumberEdit.error = getString(R.string.enter_password_format)
            return false
        }

        // 是否有网络
        if (!checkNetworkAvailable()) {
            showToast(getString(R.string.no_network))
            return false
        }
        return true
    }

    private fun loginOrRegister() {
        if (!judge()) return
        viewModel.toLoginOrRegister(Account(mUserName, mPassWord, mIsLogin))
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        // 密码不为空时显示清除按钮
        binding.loginPassNumberClear.isVisible = !p0.isNullOrEmpty()
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}