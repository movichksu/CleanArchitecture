package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonUseCaseImpl(
    private val personRepository: PersonRepository,
    private val simplifyPersonRepository: SimplifyPersonRepository
) : PersonUseCase {
    override fun subscribePersons(): Flow<List<Person>> {
        return personRepository.subscribePersons()
    }

    override suspend fun getPersons(): List<Person> {
        val persons = simplifyPersonRepository.getPersons()
        persons.forEach {
            personRepository.addPerson(it)
        }
        return persons
    }

    override fun getPersonsRx(): Observable<List<Person>> {
        return personRepository.getPersonsRx()
    }

    override suspend fun removePerson(person: Person) {
        personRepository.removePerson(person)
    }

    override suspend fun registerPerson(name: String, rate: Float) {
        val person = Person(name, rate)
        simplifyPersonRepository.addPerson(person)
    }
}
