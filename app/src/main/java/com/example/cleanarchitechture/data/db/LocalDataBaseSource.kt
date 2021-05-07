package com.example.cleanarchitechture.data.db

import android.content.Context
import androidx.room.Room
import com.example.cleanarchitechture.domain.PersonRepository
import com.example.cleanarchitechture.domain.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataBaseSource(
    context: Context
) : PersonRepository {

    private val db = Room.databaseBuilder(
        context,
        PersonDb::class.java,
        "personDataBase"
    )
        .build()

    override suspend fun getPersons(): List<Person> {
        return emptyList()
    }

    override suspend fun removePerson(person: Person) {
        withContext(Dispatchers.IO) {
            db.getPersonDao().delete(person)
        }
    }

    override suspend fun addPerson(person: Person): Boolean {
        withContext(Dispatchers.IO) {
            db.getPersonDao().insert(person)
        }
        return true
    }

    override fun getPersonsRx(): Observable<List<Person>> =
        db.getPersonDao().selectAllRx().share()

    override fun subscribePersons(): Flow<List<Person>> =
        db.getPersonDao().selectAll()
}
