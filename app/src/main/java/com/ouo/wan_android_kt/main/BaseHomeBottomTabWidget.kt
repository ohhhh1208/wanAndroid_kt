package com.ouo.wan_android_kt.main


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ouo.wan_android_kt.R
import com.ouo.wan_android_kt.home.HomePageFragment
import com.ouo.wan_android_kt.official.OfficialAccountsFragment
import com.ouo.wan_android_kt.profile.ProfileFragment
import com.ouo.wan_android_kt.project.ProjectFragment
import java.util.*

/**
 * Created by oxq on 2023/12/25.
 */

abstract class BaseHomeBottomTabWidget @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var mFragmentManager: FragmentManager? = null
    private var mFragments: ArrayList<Fragment> = arrayListOf()
    private lateinit var mViewModel: MainViewModel
    private var currentFragment: Fragment? = null

    /**
     * 外部调用初始化，传入必要的参数
     *
     * @param fm
     */
    fun init(fm: FragmentManager?, viewModel: MainViewModel) {
        mFragmentManager = fm
        mViewModel = viewModel
        if (mFragments.isEmpty()) {
            mFragments.apply {
                add(getCurrentFragment(0))
                add(getCurrentFragment(1))
                add(getCurrentFragment(2))
                add(getCurrentFragment(3))
            }
        }
        fragmentManger(viewModel.getPage() ?: 0)
    }

    /**
     * 销毁，避免内存泄漏
     */
    open fun destroy() {
        mFragmentManager?.apply {
            if (!isDestroyed)
                mFragmentManager = null
        }
        if (mFragments.isNotEmpty()) {
            mFragments.clear()
        }
    }

    /**
     * fragment的切换 实现底部导航栏的切换
     *
     * @param position 序号
     */
    protected open fun fragmentManger(position: Int) {
        mViewModel.setPage(position)
        val targetFg: Fragment = mFragments[position]
        mFragmentManager?.beginTransaction()?.apply {
            currentFragment?.apply {
                hide(this)
            }
            setReorderingAllowed(true)
            if (!targetFg.isAdded) {
                add(R.id.flHomeFragment, targetFg).commit()
            } else {
                show(targetFg).commit()
            }
        }
        currentFragment = targetFg
    }

    private val mHomeFragment: HomePageFragment by lazy { HomePageFragment.newInstance() }
    private val mProjectFragment: ProjectFragment by lazy { ProjectFragment.newInstance() }
    private val mObjectListFragment: OfficialAccountsFragment by lazy { OfficialAccountsFragment.newInstance() }
    private val mProfileFragment: ProfileFragment by lazy { ProfileFragment.newInstance() }

    private fun getCurrentFragment(index: Int): Fragment {
        return when (index) {
            0 -> mHomeFragment
            1 -> mProjectFragment
            2 -> mObjectListFragment
            3 -> mProfileFragment
            else -> mHomeFragment
        }
    }

}