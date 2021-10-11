package com.example.parliamentmembers.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

/*Name: My Mai, student ID: 2012197
Data class Entity of Room DB for storing ratings for each PM
Date: 07/10/2021
*/

@Entity(tableName = "rating_table")
data class Rating(
    @PrimaryKey(autoGenerate = true)
    val ratingId: Int = 0,
    val personNum: Int,
    val numberOfStars: Float
)