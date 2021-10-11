package com.example.parliamentmembers.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parliamentmembers.repositories.MemberRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/*Name: My Mai, student ID: 2012197
ViewModel get data from repository as a
LiveData for the Model (PartyFragment) to observe.
Also contains logic for navigation
Date: 04/10/2021
*/

class PartyViewModel(application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repo = MemberRepo(application)
    private val _parties: LiveData<List<String>>
    val parties: LiveData<List<String>>
        get() = _parties

    init {
        coroutineScope.launch {
            repo.updateMembers() //get member from retrofit and add to room
        }
        _parties = repo.getAllParties()
    }

    //navigate to Member List
    private val _navigateToMemberList = MutableLiveData<String>()
    val navigateToMemberList
        get() = _navigateToMemberList

    fun onPartyClicked(party: String) {
        _navigateToMemberList.value = party
    }

    fun onMemberListNavigated() {
        _navigateToMemberList.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


