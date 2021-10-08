package com.example.parliamentmembers.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.parliamentmembers.repositories.MemberRepo
import com.example.parliamentmembers.roomdb.Comment
import com.example.parliamentmembers.roomdb.ParliamentMember
import com.example.parliamentmembers.roomdb.Rating
import kotlin.reflect.jvm.internal.impl.resolve.constants.FloatValue

class MemberDetailViewModel(application: Application) :
    AndroidViewModel(application) {
    private val repo = MemberRepo(application)
    private lateinit var _member: LiveData<ParliamentMember>
    private lateinit var _rating: LiveData<Rating>
    private lateinit var _comment: LiveData<List<Comment>>
    private lateinit var _ratingSum: LiveData<Float>
    val ratingAvg: LiveData<Float>
    get() = _ratingSum
    val comment: LiveData<List<Comment>>
    get() = _comment
    val rating: LiveData<Rating>
    get() = _rating
    val member: LiveData<ParliamentMember>
    get() = _member

    fun setPersonalNum(newNum: Int) {
        _member = repo.getMemberByPersonNum(newNum)
    }

    fun getRatingByMem(memNum: Int) {
        _rating = repo.getRatingByPersonNum(memNum)
    }

    fun getCommentByNum(memNum: Int) {
        _comment = repo.getCommentByPersonNum(memNum)
    }

    fun getRatingAvg(memNum: Int){
        _ratingSum = repo.getRatingAvgByPersonNum(memNum)
    }



}