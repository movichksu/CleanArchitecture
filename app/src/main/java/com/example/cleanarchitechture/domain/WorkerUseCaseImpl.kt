package com.example.cleanarchitechture.domain

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.presentation.worker.addPersonWorker
import com.example.cleanarchitechture.presentation.worker.getPersonsWorker

class WorkerUseCaseImpl(
        private val workManager: WorkManager
) : WorkerUseCase {
    override fun addPersonRequest(name: String, rate: Float) {
        val addPersonRequest = OneTimeWorkRequestBuilder<addPersonWorker>()
                .setInputData(workDataOf(Constants.PERSON_NAME to name))
                .setInputData(workDataOf(Constants.PERSON_RATE to rate))
                .build()
        workManager.enqueue(addPersonRequest)
        getPersonsRequest()
    }

    override fun getPersonsRequest() {
        val getPersonsRequest = OneTimeWorkRequestBuilder<getPersonsWorker>()
                .build()
        workManager.enqueue(getPersonsRequest)
    }
}