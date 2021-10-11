package com.example.parliamentmembers.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.parliamentmembers.repositories.MemberRepo
import com.example.parliamentmembers.roomdb.Comment
import com.example.parliamentmembers.roomdb.ParliamentMember

/*Name: My Mai, student ID: 2012197
ViewModel get data from repository as a
LiveData for the Model (MembersInfoFragment) to observe.
Date: 06/10/2021
*/

class MemberDetailViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repo = MemberRepo(application)
    private lateinit var _member: LiveData<ParliamentMember>
    private lateinit var _comment: LiveData<List<Comment>>
    private lateinit var _ratingAvg: LiveData<Float>
    val ratingAvg: LiveData<Float>
        get() = _ratingAvg
    val comment: LiveData<List<Comment>>
        get() = _comment
    val member: LiveData<ParliamentMember>
        get() = _member

    fun setPersonalNum(newNum: Int) {
        _member = repo.getMemberByPersonNum(newNum)
    }

    fun insertRating(personNum: Int, rating: Float) {
        repo.addRating(personNum, rating)
    }

    fun insertComment(personNum: Int, comment: String) {
        repo.addComment(personNum, comment)
    }

    fun getRatingAvg(memNum: Int) {
        _ratingAvg = repo.getRatingAvgByPersonNum(memNum)
    }

    fun getCommentByNum(memNum: Int) {
        _comment = repo.getCommentByPersonNum(memNum)
    }
}