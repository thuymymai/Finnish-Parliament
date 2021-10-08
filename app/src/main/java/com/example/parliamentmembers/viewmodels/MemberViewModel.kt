package com.example.parliamentmembers.viewmodels

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.parliamentmembers.fragments.MembersFragment
import com.example.parliamentmembers.fragments.MembersFragmentArgs
import com.example.parliamentmembers.repositories.MemberRepo
import com.example.parliamentmembers.roomdb.ParliamentMember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MemberViewModel(application: Application):
    AndroidViewModel(application) {
    private lateinit var _member: LiveData<List<ParliamentMember>>
    private var viewModelJob = Job()
    private val repo = MemberRepo(application)
    val members: LiveData<List<ParliamentMember>>
        get() = _member

    private lateinit var party: String
    fun setParty(newParty: String) {
        party = newParty
        _member = repo.getMemberByParty(party)
    }

    private val _navigateToMemberDetails = MutableLiveData<Int>()
    val navigateToMemberDetails
        get() = _navigateToMemberDetails

    fun onMemberClicked(id: Int) {
        _navigateToMemberDetails.value = id
    }
    fun onMemberDetailNavigated() {
        _navigateToMemberDetails.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}