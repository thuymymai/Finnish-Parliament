package com.example.parliamentmembers.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parliamentmembers.repositories.MemberRepo
import kotlinx.coroutines.*


class PartyViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _parties: LiveData<List<String>>
    private var viewModelJob = Job()
    private val repo = MemberRepo(application)
    val parties: LiveData<List<String>>
        get() = _parties

    init {
        _parties = repo.getAllParties()
    }

    /*private fun getMember() {
        coroutineScope.launch {
            val getPropertiesDeferred = api.getMember()
            try {
                val listResult = getPropertiesDeferred.await()
                Timber.i(listResult.toString())
                _response.value =
                    "Success: ${listResult.size} members of parliament properties retrieved"
                repo.updateMembers()

            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }*/

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

