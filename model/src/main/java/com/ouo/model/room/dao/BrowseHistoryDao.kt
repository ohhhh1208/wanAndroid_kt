package com.ouo.model.room.dao

import androidx.room.*
import com.ouo.model.room.entity.Article

/**
 * Created by oxq on 2023/12/19.
 */

@Dao
interface BrowseHistoryDao {

    @Query("SELECT * FROM browse_history")
    suspend fun getAllArticle(): List<Article>

    @Query("SELECT * FROM browse_history where id = :id and local_type = :type")
    suspend fun getArticle(id: Int,type: Int): Article?

    @Query("SELECT * FROM browse_history where local_type = :type order by uid desc limit :page,20")
    suspend fun getHistoryArticleList(page: Int, type: Int): List<Article>

    @Query("SELECT * FROM browse_history where local_type = :type")
    suspend fun getArticleList(type: Int): List<Article>

    @Query("SELECT * FROM browse_history where local_type = :type")
    suspend fun getTopArticleList(type: Int): List<Article>

    @Query("SELECT * FROM browse_history where local_type = :type and chapter_id = :chapterId")
    suspend fun getArticleListForChapterId(type: Int, chapterId: Int): List<Article>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(articleList: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Update
    suspend fun update(article: Article): Int

    @Delete
    suspend fun delete(article: Article): Int

    @Delete
    suspend fun deleteList(articleList: List<Article>): Int

    @Query("DELETE FROM browse_history")
    suspend fun deleteAll()

    @Query("DELETE FROM browse_history where local_type = :type")
    suspend fun deleteAll(type: Int)

    @Query("DELETE FROM browse_history where local_type = :type and  chapter_id = :chapterId")
    suspend fun deleteAll(type: Int, chapterId: Int)

}