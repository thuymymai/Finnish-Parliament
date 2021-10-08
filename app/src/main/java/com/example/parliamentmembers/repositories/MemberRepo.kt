package com.example.parliamentmembers.repositories


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.parliamentmembers.api.RetrofitInstance.api
import com.example.parliamentmembers.roomdb.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MemberRepo(val context: Context) {
    private lateinit var memberDao: MemberDao
    //private lateinit var ratingDao: RatingDao
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Default)

    init {
        val database: MemberDB? = MemberDB.getInstance(context)
        if (database != null) {
            memberDao = database.memberDatabaseDao
            //ratingDao = database.ratingDao
        }
        //memberDetails = memberDao.readAllData()
    }

    suspend fun updateMembers() {
        coroutineScope.launch {
            try {
                val getPropertiesDeferred = api.getMember()
                val listResult = getPropertiesDeferred.await()
                val mps = listResult as MutableList<ParliamentMember>
                mps.forEach { (MemberDB.getInstance(context).memberDatabaseDao.insert(it)) }
            } catch (e: Exception) {
                Timber.i(e.message ?: "Nothing")
            }
        }
    }

    /*fun insertRating(rating: Rating){
        MemberDB.getInstance(context).ratingDao.insert(rating)
    }*/
    fun getRatingAvgByPersonNum(key: Int): LiveData<Float> = MemberDB.getInstance(context).ratingDao.getSumByPersonNum(key)
    fun getCommentByPersonNum(key: Int): LiveData<List<Comment>> = MemberDB.getInstance(context).commentDao.getCommentByPersonNum(key)
    fun getRatingByPersonNum(key: Int): LiveData<Rating> = MemberDB.getInstance(context).ratingDao.getByPersonNum(key)
    fun getMemberByPersonNum(key: Int): LiveData<ParliamentMember> = memberDao.getByPersonNum(key)
    fun getMemberByParty(key: String): LiveData<List<ParliamentMember>> = memberDao.getByParty(key)
    fun getAllParties(): LiveData<List<String>> = memberDao.getParty()

}
