package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonRepository : SimplifyPersonRepository {
    suspend fun removePerson(person: Person)
    fun getPersonsRx(): Observable<List<Person>>
    fun subscribePersons(): Flow<List<Person>>
}

interface SimplifyPersonRepository {
    suspend fun getPersons(): List<Person>
    suspend fun addPerson(person: Person): Boolean
}
