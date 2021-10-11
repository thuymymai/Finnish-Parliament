package com.example.parliamentmembers.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parliamentmembers.repositories.MemberRepo
import com.example.parliamentmembers.roomdb.ParliamentMember
import kotlinx.coroutines.Job

/*Name: My Mai, student ID: 2012197
ViewModel get data from repository as a
LiveData for the Model (MembersFragment) to observe.
Also contains logic for navigation
Date: 05/10/2021
*/

class MemberViewModel(application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val repo = MemberRepo(application)
    private lateinit var _member: LiveData<List<ParliamentMember>>
    val members: LiveData<List<ParliamentMember>>
        get() = _member

    //this function take data passed from Party Fragment as para
    //I call it in the Fragment to pass data to this viewmodel to get member by party in repo
    private lateinit var party: String
    fun setParty(newParty: String) {
        party = newParty
        _member = repo.getMemberByParty(party)
    }

    //navigate to MemberDetails
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