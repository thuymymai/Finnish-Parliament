package com.example.parliamentmembers.api

import com.example.parliamentmembers.roomdb.ParliamentMember
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/*Name: My Mai, student ID: 2012197
This interface defines how Retrofit talks to the web server using HTTP requests.
It has @GET request and function to get response string from web server
Date: 29/09/2021
*/

interface MemberApiService {

    @GET("~peterh/mps.json")
    fun getMemberAsync():
            Deferred<List<ParliamentMember>>
}


