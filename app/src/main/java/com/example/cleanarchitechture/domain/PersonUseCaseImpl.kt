package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonUseCaseImpl(
    private val personRepository: PersonRepository
) : PersonUseCase {
    override fun getPersons(): Flow<List<Person>> {
        return personRepository.getPersons()
    }

    override fun getPersonsRx(): Observable<List<Person>> {
        return personRepository.getPersonsRx()
    }

    override suspend fun removePerson(person: Person) {
        personRepository.removePerson(person)
    }

    override suspend fun registerPerson(name: String, rate: Int) {
        val person = Person(name, rate)
        personRepository.addPerson(person)
    }
}
