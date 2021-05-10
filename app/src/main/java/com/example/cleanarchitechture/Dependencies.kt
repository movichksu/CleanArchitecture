package com.example.cleanarchitechture

import androidx.work.WorkManager
import com.example.cleanarchitechture.data.db.LocalDataBaseSource
import com.example.cleanarchitechture.domain.*
import com.example.cleanarchitechture.data.server.CloudSource
import com.example.cleanarchitechture.data.system.WorkerExecutor

object Dependencies {

    private val cloudSource: SimplifyPersonRepository by lazy{CloudSource()}
    private val LocalDatabaseSource: PersonRepository by lazy { LocalDataBaseSource(App.instance) }

    private fun getPersonsRepository(): PersonRepository {
        return LocalDatabaseSource
    }
    private fun getWorkersRepository(): PersonWorkExecutor{
        return WorkerExecutor(WorkManager.getInstance(App.instance))
    }

    fun getPersonUseCase(): PersonUseCase {
        return PersonUseCaseImpl(getPersonsRepository(), cloudSource)
    }

    fun getWorkerUseCase(): WorkerUseCase{
        return WorkerUseCaseImpl(getWorkersRepository())
    }
}
