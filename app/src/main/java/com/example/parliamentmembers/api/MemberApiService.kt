package com.example.parliamentmembers.api

import com.example.parliamentmembers.roomdb.ParliamentMember
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET


interface MemberApiService {

    @GET("~peterh/mps.json")
    fun getMember():
            Deferred<List<ParliamentMember>>
}


