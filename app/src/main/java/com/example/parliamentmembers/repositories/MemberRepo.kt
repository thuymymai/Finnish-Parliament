package com.example.parliamentmembers.repositories


import android.content.Context
import androidx.lifecycle.LiveData
import com.example.parliamentmembers.api.RetrofitInstance.api
import com.example.parliamentmembers.roomdb.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/*Name: My Mai, student ID: 2012197
This class received data fetched from api and also get data from ROOM
It helps centralize the data access from many sources
Date: 30/09/2021
*/

class MemberRepo(val context: Context) {
    private var memberDao: MemberDao
    private var ratingDao: RatingDao
    private var commentDao: CommentDao
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Default)

    init {
        val database: MemberDB = MemberDB.getInstance(context)
        memberDao = database.memberDatabaseDao
        ratingDao = database.ratingDao
        commentDao = database.commentDao
    }

    //get data from retrofit and insert it to room database
    suspend fun updateMembers() {
        coroutineScope.launch {
            try {
                val getPropertiesDeferred = api.getMemberAsync()
                val listResult = getPropertiesDeferred.await()
                val members = listResult as MutableList<ParliamentMember>
                members.forEach { (memberDao.insert(it)) }
            } catch (e: Exception) {
                Timber.d(e.message ?: "Nothing")
            }
        }
    }

    //get data from DAOs
    fun getMemberByPersonNum(key: Int): LiveData<ParliamentMember> = memberDao.getByPersonNum(key)
    fun getMemberByParty(key: String): LiveData<List<ParliamentMember>> = memberDao.getByParty(key)
    fun getAllParties(): LiveData<List<String>> = memberDao.getParty()
    fun getRatingAvgByPersonNum(key: Int): LiveData<Float> = ratingDao.getSumByPersonNum(key)
    fun getCommentByPersonNum(key: Int): LiveData<List<Comment>> =
        commentDao.getCommentByPersonNum(key)


}
