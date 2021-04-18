package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPersons(): Flow<List<Person>>
    suspend fun removePerson(person: Person)
    suspend fun addPerson(person: Person)
    fun getPersonsRx(): Observable<List<Person>>
}
