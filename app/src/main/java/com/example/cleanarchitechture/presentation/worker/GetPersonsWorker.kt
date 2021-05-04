package com.example.cleanarchitechture.presentation.worker

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.JobIntentService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.PersonUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.Duration

class GetPersonsWorker(
        private val context: Context,
        workerParameters: WorkerParameters
): Worker(context, workerParameters ) {
    private val personUseCase: PersonUseCase = Dependencies.getPersonUseCase()
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun doWork(): Result {
        var result: Result = Result.success()
        ioScope.launch {
             result = if(personUseCase.getPersons().isEmpty()){ Result.success() } else { Result.retry() }
        }
        return result
    }


}