package com.example.cleanarchitechture.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cleanarchitechture.App
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.domain.PersonUseCase
import kotlinx.coroutines.*

class AddPersonService : Service() {

    companion object {
        const val TAG = "PersonService"
        const val CHANNEL_ID = "PersonServiceChannel"
        const val NOTIFICATION_ID = 1
        const val notificationTitle = "New person!"
    }

    private val personUseCase: PersonUseCase = Dependencies.getPersonUseCase()
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())
    private val binder = PersonServiceBinder()


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        val name = intent.getStringExtra(Constants.PERSON_NAME) ?: ""
        val rate = intent.getFloatExtra(Constants.PERSON_RATE, 0f)
        startAddPersonProcess(name, rate)

        return super.onStartCommand(intent, flags, startId)
    }

    fun startAddPersonProcess(name: String, rate: Float) {
        ioScope.launch {
            createNotificationChannel()
            val builder = NotificationCompat.Builder(App.instance, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_person)
                    .setContentTitle(notificationTitle)
                    .setContentText("a new person $name -- $rate is added!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()
            val notificationManager = NotificationManagerCompat.from(App.instance)
            notificationManager.notify(NOTIFICATION_ID, builder)

            personUseCase.registerPerson(name, rate)
            Intent().also {
                it.action = Constants.ADDED_PERSON_ACTION
                sendBroadcast(it)
            }

            notificationManager.cancel(NOTIFICATION_ID)

        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Person Notification Channel"
            val descriptionText = "Is a new person"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.cancel()
    }

    inner class PersonServiceBinder : Binder() {
        fun getService(): AddPersonService =
            this@AddPersonService
    }
}