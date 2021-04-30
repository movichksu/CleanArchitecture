package com.example.cleanarchitechture.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.JobIntentService
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.PersonUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GetPersonService:JobIntentService() {

    private val personUseCase: PersonUseCase = Dependencies.getPersonUseCase()
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onHandleWork(intent: Intent) {
        ioScope.launch {
            personUseCase.getPersons()
        }
    }

}