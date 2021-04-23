package com.example.cleanarchitechture.domain

import androidx.room.PrimaryKey
import com.example.cleanarchitechture.entity.Person
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
        return simplifyPersonRepository.getPersons()
    }

    override fun getPersonsRx(): Observable<List<Person>> {
        return personRepository.getPersonsRx()
    }

    override suspend fun removePerson(person: Person) {
        personRepository.removePerson(person)
    }

    override suspend fun registerPerson(name: String, rate: Int) {
        val person = Person(name, rate)
        simplifyPersonRepository.addPerson(person)
    }
}
