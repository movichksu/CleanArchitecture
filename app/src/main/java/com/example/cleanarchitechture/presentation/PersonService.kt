package com.example.cleanarchitechture.presentation

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.PersonUseCase
import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.*

class PersonService: Service() {

    companion object {
        const val TAG = "PersonService"
    }

    private val personUseCase: PersonUseCase = Dependencies.getPersonUseCase()
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())



    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        val name = intent.getStringExtra(Constants.PERSON_NAME) ?: ""
        val rate = intent.getFloatExtra(Constants.PERSON_RATE, 0f)

        ioScope.launch { personUseCase.registerPerson(name, rate) }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
    }

}