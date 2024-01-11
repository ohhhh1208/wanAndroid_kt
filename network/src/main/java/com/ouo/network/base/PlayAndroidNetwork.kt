package com.ouo.network.base

import com.ouo.network.service.*

/**
 * Created by oxq on 2023/12/19.
 */

//object 关键字用于创建单例对象，在该单例对象中定义的方法可以被视为静态方法的等效物，
// 它们可以在不创建对象实例的情况下直接通过单例对象进行访问。
object PlayAndroidNetwork {
    private val homePageService = ServiceCreator.create(HomePageService::class.java)

    suspend fun getBanner() = homePageService.getBanner()

    suspend fun getTopArticleList() = homePageService.getTopArticle()

    suspend fun getArticleList(page: Int) = homePageService.getArticle(page)

    suspend fun getHotKey() = homePageService.getHotKey()

    suspend fun getQueryArticleList(page: Int, k: String) =
        homePageService.getQueryArticleList(page, k)

    private val projectService = ServiceCreator.create(ProjectService::class.java)

    suspend fun getProjectTree() = projectService.getProjectTree()

    suspend fun getProject(page: Int, cid: Int) = projectService.getProject(page, cid)

    private val officialService = ServiceCreator.create(OfficialService::class.java)

    suspend fun getWxArticleTree() = officialService.getWxArticleTree()

    suspend fun getWxArticle(page: Int, cid: Int) = officialService.getWxArticle(page, cid)

    private val loginService = ServiceCreator.create(LoginService::class.java)

    suspend fun getLogin(username: String, password: String) =
        loginService.getLogin(username, password)

    suspend fun getRegister(username: String, password: String, repassword: String) =
        loginService.getRegister(username, password, repassword)

    suspend fun getLogout() = loginService.getLogout()

    private val shareService = ServiceCreator.create(ShareService::class.java)

    suspend fun getMyShareList(page: Int) = shareService.getMyShareList(page)

    suspend fun getShareList(cid: Int, page: Int) = shareService.getShareList(cid, page)

    suspend fun deleteMyArticle(cid: Int) = shareService.deleteMyArticle(cid)

    suspend fun shareArticle(title: String, link: String) =
        shareService.shareArticle(title, link)

    private val rankService = ServiceCreator.create(RankService::class.java)

    suspend fun getRankList(page: Int) = rankService.getRankList(page)

    suspend fun getUserRank(page: Int) = rankService.getUserRank(page)

    suspend fun getUserInfo() = rankService.getUserInfo()

    private val collectService = ServiceCreator.create(CollectService::class.java)

    suspend fun getCollectList(page: Int) = collectService.getCollectList(page)

    suspend fun toCollect(id: Int) = collectService.toCollect(id)

    suspend fun cancelCollect(id: Int) = collectService.cancelCollect(id)
}