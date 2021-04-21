package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonUseCase {
    fun subscribePersons(): Flow<List<Person>>
    fun getPersons(): List<Person>
    fun getPersonsRx(): Observable<List<Person>>
    suspend fun removePerson(person: Person)
    suspend fun registerPerson(name: String, rate: Int)
}
