package com.example.cleanarchitechture.domain

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.presentation.worker.AddPersonWorker
import com.example.cleanarchitechture.presentation.worker.GetPersonsWorker

class WorkerUseCaseImpl(
        private val workManager: WorkManager
) : WorkerUseCase {
    override fun addPersonRequest(name: String, rate: Float) {
        val addPersonRequest = OneTimeWorkRequestBuilder<AddPersonWorker>()
                .setInputData(workDataOf(Constants.PERSON_NAME to name))
                .setInputData(workDataOf(Constants.PERSON_RATE to rate))
                .build()
        workManager.enqueue(addPersonRequest)
        getPersonsRequest()
    }

    override fun getPersonsRequest() {
        val getPersonsRequest = OneTimeWorkRequestBuilder<GetPersonsWorker>()
                .build()
        workManager.enqueue(getPersonsRequest)
    }
}