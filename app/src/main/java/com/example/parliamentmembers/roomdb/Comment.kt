package com.example.parliamentmembers.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

/*Name: My Mai, student ID: 2012197
Data class Entity of Room DB for storing comments for each PM
Date: 07/10/2021
*/

@Entity(tableName = "comment_table")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val commentID: Int = 0,
    val personNum: Int,
    val comment: String
) {

}