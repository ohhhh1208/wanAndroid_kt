package com.ouo.model.room.dao

import androidx.room.*
import com.ouo.model.room.entity.BannerBean

/**
 * Created by oxq on 2023/12/19.
 */
@Dao
interface BannerBeanDao {

    @Query("SELECT * FROM banner_bean order by uid desc")
    suspend fun getBannerBeanList(): List<BannerBean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(BannerBeanList: List<BannerBean>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(BannerBean: BannerBean)

    @Update
    suspend fun update(BannerBean: BannerBean): Int

    @Delete
    suspend fun delete(BannerBean: BannerBean): Int

    @Delete
    suspend fun deleteList(BannerBeanList: List<BannerBean>): Int

    @Query("DELETE FROM banner_bean")
    suspend fun deleteAll()
}