package com.example.parliamentmembers.roomdb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/*Name: My Mai, student ID: 2012197
Data class Entity of Room DB for storing Parliament Member
Date: 29/09/2021
*/

@Entity(tableName = "parliament_members_table")
data class ParliamentMember(
    @PrimaryKey(autoGenerate = true)
    val personNumber: Int,
    val seatNumber: Int = 0,
    val last: String,
    val first: String,
    val party: String,
    val minister: Boolean = false,
    val picture: String = "",
    val twitter: String = "",
    val bornYear: Int = 0,
    val constituency: String = ""
)