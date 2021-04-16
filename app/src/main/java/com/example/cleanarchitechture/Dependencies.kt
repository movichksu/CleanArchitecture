package com.example.cleanarchitechture

import android.content.Context
import com.example.cleanarchitechture.db.LocalDataBaseSource
import com.example.cleanarchitechture.domain.*

object Dependencies {

    private val personRepository: PersonRepository by lazy { LocalDataBaseSource(App.instance) }

    private fun getPersonsRepository(context: Context): PersonRepository {
        return LocalDataBaseSource(context)
    }

    fun getPersonUseCase(context: Context): PersonUseCase {
        return PersonUseCaseImpl(getPersonsRepository(context))
    }
}
