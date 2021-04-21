package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonUseCase {
    fun getPersons(): Flow<List<Person>>
    fun getPersonsRx(): Observable<List<Person>>
    suspend fun removePerson(person: Person)
    suspend fun registerPerson(name: String, rate: Int)
}
