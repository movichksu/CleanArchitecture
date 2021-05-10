package com.example.cleanarchitechture.domain

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.presentation.worker.AddPersonWorker
import com.example.cleanarchitechture.presentation.worker.GetPersonsWorker

class WorkerUseCaseImpl(
        private val workExecutor: PersonWorkExecutor
) : WorkerUseCase {
    override fun addPersonRequest(name: String, rate: Float) {
        workExecutor.addPersonRequest(name, rate)
    }

    override fun getPersonsRequest() {
        workExecutor.getPersonsRequest()
    }
}