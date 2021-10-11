package com.example.parliamentmembers.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.parliamentmembers.repositories.MemberRepo
import retrofit2.HttpException
import timber.log.Timber


/*Name: My Mai, student ID: 2012197
This class is the place to define the actual work running in background
in this case updating members from api service
Date: 07/10/2021
*/

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val repository = MemberRepo(applicationContext)

        try {
            repository.updateMembers()
            Timber.d("WorkManager: Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.example.parliamentmembers.workmanager.RefreshDataWorker"
    }
}

