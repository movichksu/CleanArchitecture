package com.example.cleanarchitechture

import androidx.work.WorkManager
import com.example.cleanarchitechture.db.LocalDataBaseSource
import com.example.cleanarchitechture.domain.*
import com.example.cleanarchitechture.server.CloudSource

object Dependencies {

    private val cloudSource: SimplifyPersonRepository by lazy{CloudSource()}
    private val LocalDatabaseSource: PersonRepository by lazy { LocalDataBaseSource(App.instance) }

    private fun getPersonsRepository(): PersonRepository {
        return LocalDatabaseSource
    }

    fun getPersonUseCase(): PersonUseCase {
        return PersonUseCaseImpl(getPersonsRepository(), cloudSource)
    }

    fun getWorkerUseCase(): WorkerUseCase{
        return WorkerUseCaseImpl(WorkManager.getInstance(App.instance))
    }
}
