package com.example.parliamentmembers.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val commentID: Int = 0,
    val personNum: Int,
    val comment: String
) {

}