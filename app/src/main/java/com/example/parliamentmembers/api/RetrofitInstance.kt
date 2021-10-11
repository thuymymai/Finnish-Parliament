package com.example.parliamentmembers.api

import com.example.parliamentmembers.util.Constants.Companion.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/*Name: My Mai, student ID: 2012197
This object declaration is for singleton of object Retrofit
with set up of Moshi converter
Date: 29/09/2021
*/

object RetrofitInstance {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    //public singleton object
    val api: MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java)
    }
}