package com.example.cleanarchitechture

import com.example.cleanarchitechture.db.LocalDataBaseSource
import com.example.cleanarchitechture.domain.*

object Dependencies {

    private val personRepository: PersonRepository by lazy { LocalDataBaseSource(App.instance) }

    private fun getPersonsRepository(): PersonRepository {
        return personRepository
    }

    fun getPersonUseCase(): PersonUseCase {
        return PersonUseCaseImpl(getPersonsRepository())
    }
}
