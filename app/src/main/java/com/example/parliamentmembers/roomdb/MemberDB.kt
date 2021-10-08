package com.example.parliamentmembers.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 8, entities = [(ParliamentMember::class), (Rating::class), (Comment::class)], exportSchema = true)
abstract class MemberDB : RoomDatabase() {

    abstract val memberDatabaseDao: MemberDao
    abstract val ratingDao: RatingDao
    abstract val commentDao: CommentDao

    companion object {

        @Volatile
        private var INSTANCE: MemberDB? = null

        fun getInstance(context: Context): MemberDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemberDB::class.java,
                        "member_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
