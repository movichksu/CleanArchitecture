package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    suspend fun getPersons(): Flow<List<Person>>
    suspend fun removePerson(person: Person)
    suspend fun addPerson(person: Person)
}
