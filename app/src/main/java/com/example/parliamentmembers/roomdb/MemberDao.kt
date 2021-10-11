package com.example.parliamentmembers.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(member: ParliamentMember)

    @Update
    fun update(member: ParliamentMember)

    @Query("SELECT DISTINCT party from parliament_members_table")
    fun getParty(): LiveData<List<String>>

    @Query("SELECT * from parliament_members_table where party == :key order by first ASC")
    fun getByParty(key: String): LiveData<List<ParliamentMember>>

    @Query("SELECT * from parliament_members_table where personNumber == :key")
    fun getByPersonNum(key: Int): LiveData<ParliamentMember>

    @Query("SELECT * FROM parliament_members_table")
    fun getAll(): List<ParliamentMember>

    @Query("SELECT * FROM parliament_members_table")
    fun readAllData(): LiveData<List<ParliamentMember>>

    @Query("DELETE FROM parliament_members_table")
    fun deleteAll()
}

@Dao
interface RatingDao {
    @Insert
    fun insert(rating: Rating)

    /*@Query("SELECT * FROM rating_table where id == :key")
    fun getAllRatings(key: Long): Rating*/

    @Query("SELECT * FROM rating_table where personNum == :key")
    fun getByPersonNum(key: Int): LiveData<Rating>

    @Query("SELECT avg(numberOfStars) FROM rating_table where personNum == :key")
    fun getSumByPersonNum(key: Int): LiveData<Float>
}

@Dao
interface CommentDao {
    @Insert
    fun insert(comment: Comment)

    @Query("SELECT * FROM comment_table where personNum == :key")
    fun getCommentByPersonNum(key: Int): LiveData<List<Comment>>

    @Delete
    fun deleteComment(comment: Comment)
}