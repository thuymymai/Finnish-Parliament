package com.example.parliamentmembers.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parliamentmembers.api.RetrofitInstance.api
import com.example.parliamentmembers.repositories.MemberRepo
import kotlinx.coroutines.*
import timber.log.Timber


class PartyViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _parties: LiveData<List<String>>
    private val _response = MutableLiveData<String>()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repo = MemberRepo(application)

    val response: LiveData<String>
        get() = _response

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

