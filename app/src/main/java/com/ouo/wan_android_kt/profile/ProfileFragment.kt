package com.ouo.wan_android_kt.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.ouo.core.Play
import com.ouo.core.Play.logout
import com.ouo.wan_android_kt.R

import com.ouo.wan_android_kt.databinding.FragmentProfileBinding
import com.ouo.wan_android_kt.home.ArticleCollectBaseFragment
import com.ouo.wan_android_kt.main.login.AccountRepository
import com.ouo.wan_android_kt.main.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : ArticleCollectBaseFragment(), View.OnClickListener {

    private var binding: FragmentProfileBinding? = null
    private var dialog: AlertDialog? = null

    @Inject
    lateinit var accountRepository: AccountRepository

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, attachToRoot)
        return binding!!.root
    }

    override fun initData() {

    }

    override fun initView() {
        binding?.apply {
            profileBtnLogout.setOnClickListener(this@ProfileFragment)
            profileIvHead.setOnClickListener(this@ProfileFragment)
            profileTvName.setOnClickListener(this@ProfileFragment)
            profileTvRank.setOnClickListener(this@ProfileFragment)
            profileBtnLogout.setOnClickListener(this@ProfileFragment)
        }
    }

    /**
     * 使用协程来观察登录状态的变化，并根据不同的状态更新 UI
     */
    override fun refreshData() {
        lifecycleScope.launch {
            // lifecycleScope.launch 创建了一个协程作用域
            //当 lifecycleScope 所绑定的 LifecycleOwner（通常是 Activity 或 Fragment）的生命周期状态达到 DESTROYED 状态时，
            // lifecycleScope 会自动取消其中的所有协程
            Play.isLogin().collectLatest {
                //使用 Flow 的 collectLatest 操作符来监听 Play.isLogin() 返回的登录状态的 Flow
                //当登录状态发生变化时，会执行传入的 lambda 表达式
                binding?.apply {
                    //it 是一个隐式的参数名，用于代表 Play.isLogin() 返回的最新值
                    if (it) {
                        profileIvHead.setImageResource(R.drawable.ic_head)
                        profileTvName.text = Play.nickName
                        profileTvRank.text = Play.username
                        profileBtnLogout.visibility = View.VISIBLE
                    } else {
                        clearInfo()
                    }
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.profileIvHead, R.id.profileTvName, R.id.profileTvRank -> {
                personalInformation()
            }
            R.id.profileBtnLogout -> {
                setLogout()
            }
            R.id.logout_cancel -> {
                dialog?.dismiss()
            }
            R.id.logout_sure -> {
                dialog?.dismiss()
                clearInfo()
                logout()
                accountRepository.getLogout()
            }
        }
    }

    /**
     * 清除数据
     */
    private fun clearInfo() {
        binding?.apply {
            profileBtnLogout.visibility = View.GONE
            profileIvHead.setImageResource(R.drawable.img_nomal_head)
            profileTvName.text = getString(R.string.no_login)
            profileTvRank.text = getString(R.string.click_login)
        }
    }

    /**
     * 退出登录对话框
     */
    private fun setLogout() {
        //通过 LayoutInflater 对象获取布局文件对应的 View 对象，并设置其点击事件为当前 Fragment
        val view = LayoutInflater.from(context).inflate(R.layout.layout_logout, null)
        view.apply {
            findViewById<TextView>(R.id.logout_cancel).setOnClickListener(this@ProfileFragment)
            findViewById<TextView>(R.id.logout_sure).setOnClickListener(this@ProfileFragment)
        }
        //创建一个对话框对象并将 View 对象设置为其内容视图，最后设置对话框的背景为透明，并显示出来
        dialog = AlertDialog.Builder(context!!).setView(view).create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.show()
    }

    private fun personalInformation() {
        if (!Play.isLoginResult()) {
            //跳转至登录页
            LoginActivity.actionStart(requireContext())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

}