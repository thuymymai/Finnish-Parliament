package com.example.parliamentmembers.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rating_table")
data class Rating(
    @PrimaryKey(autoGenerate = true)
    val ratingId: Int = 0,
    val personNum: Int,
    val numberOfStars: Float
)