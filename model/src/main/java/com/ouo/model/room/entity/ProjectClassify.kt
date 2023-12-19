package com.ouo.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by oxq on 2023/12/19.
 */

@Entity(tableName = "project_classify")
data class ProjectClassify(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    //@ColumnInfo(name = "children") val children: List<Any> = arrayListOf(),
    @ColumnInfo(name = "course_id") val courseId: Int,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "order_classify") val order: Int,
    @ColumnInfo(name = "parent_chapter_id") val parentChapterId: Int,
    @ColumnInfo(name = "user_control_set_top") val userControlSetTop: Boolean,
    @ColumnInfo(name = "visible") val visible: Int
)
