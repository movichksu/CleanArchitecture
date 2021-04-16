package com.example.cleanarchitechture

import android.app.Application
import com.example.cleanarchitechture.db.LocalDataBaseSource
import com.example.cleanarchitechture.domain.*

object Dependencies {

    private var personRepository: PersonRepository? = null

    private fun getPersonsRepository(application: Application): PersonRepository {
        return personRepository ?: LocalDataBaseSource(application).apply {
            personRepository = this
        }
    }

    fun getPersonUseCase(application: Application): PersonUseCase {
        return PersonUseCaseImpl(getPersonsRepository(application))
    }
}
