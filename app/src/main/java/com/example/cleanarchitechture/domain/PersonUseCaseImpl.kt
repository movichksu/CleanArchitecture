package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.flow.Flow

class PersonUseCaseImpl(
        private val personRepository: PersonRepository
) : PersonUseCase {
    override suspend fun getPersons(): Flow<List<Person>> {
        return personRepository.getPersons()
    }

    override suspend fun removePerson(person: Person) {
        personRepository.removePerson(person)
    }

    override suspend fun registerPerson(name: String, rate: Int) {
        val person = Person(name, rate)
        personRepository.addPerson(person)
    }
}
