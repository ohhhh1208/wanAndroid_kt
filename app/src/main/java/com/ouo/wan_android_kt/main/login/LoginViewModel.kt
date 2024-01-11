package com.ouo.wan_android_kt.main.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ouo.core.Play
import com.ouo.core.util.showToast
import com.ouo.model.model.Login
import com.ouo.wan_android_kt.R
import com.ouo.wan_android_kt.article.ArticleBroadCast
import com.ouo.wan_android_kt.base.http
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by oxq on 2024/1/11.
 *
 * 注册、登录
 */

//@HiltViewModel 注解通常与 @Inject 注解配合使用，以便获得依赖项注入的支持
@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val accountRepository: AccountRepository
) : AndroidViewModel(application) {

    //MutableLiveData 是一种可变的、观察者模式的数据持有类，它提供了便利的方式来处理 UI 数据的更新和观察
    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> get() = _state

    fun toLoginOrRegister(account: Account) {
        _state.postValue(Logging)
        if (account.isLogin) {
            login(account)
        } else {
            register(account)
        }
    }

    private fun login(account: Account) {
        viewModelScope.http(
            request = {
                accountRepository.getLogin(account.username, account.password)
            },
            response = {
                //it 在这里表示 API 请求成功后返回的结果对象，即accountRepository.getLogin的结果
                success(it, account.isLogin)
            },
            error = {
                _state.postValue(LoginError)
            }
        )
    }

    private fun register(account: Account) {
        viewModelScope.http(
            request = {
                accountRepository.getRegister(
                    account.username,
                    account.password,
                    account.password
                )
            },
            response = { success(it, account.isLogin) },
            error = { _state.postValue(LoginError) }
        )
    }

    private fun success(it: Login?, isLogin: Boolean) {
        it ?: return
        _state.postValue(LoginSuccess(it))
        Play.setLogin(true)
        Play.setUserInfo(it.nickname, it.username)
        ArticleBroadCast.sendArticleChangesReceiver(context = getApplication())
        getApplication<Application>().showToast(
            if (isLogin) getApplication<Application>().getString(R.string.login_success)
            else getApplication<Application>().getString(
                R.string.register_success
            )
        )
    }
}


data class Account(val username: String, val password: String, val isLogin: Boolean)

//sealed关键字修饰的类就是密封类。密封类其实是一种特殊的抽象类，专门用于派生子类的。
//密封类的子类是固定的，密封类的直接子类必须和密封类在同一个文件中
//密封类间接的子类可以在不同文件中，密封类所有的构造方法都是private的
sealed class LoginState

//object 关键字用于创建单例对象，在该单例对象中定义的方法可以被视为静态方法的等效物，
// 它们可以在不创建对象实例的情况下直接通过单例对象进行访问。
object Logging : LoginState()

data class LoginSuccess(val login: Login) : LoginState()

object LoginError : LoginState()
