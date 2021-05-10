package com.example.cleanarchitechture.presentation.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.PersonUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddPersonWorker(
        private val context: Context,
        workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters ) {
    private val personUseCase: PersonUseCase = Dependencies.getPersonUseCase()

    override suspend fun doWork(): Result {
        var result: Result = Result.success()

            val name = inputData.getString(Constants.PERSON_NAME) ?: ""
            val rate = inputData.getFloat(Constants.PERSON_RATE, 0f)
            if (name.isEmpty() || rate == 0f){
                result = Result.failure()
            }
            else
            {
                result = Result.success()
                personUseCase.registerPerson(name,rate)
            }

        return result
    }
}
