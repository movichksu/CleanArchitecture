package com.example.cleanarchitechture.db

import android.content.Context
import androidx.room.Room
import com.example.cleanarchitechture.domain.PersonRepository
import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class LocalDataBaseSource(
        context: Context
) : PersonRepository {

    private val db = Room.databaseBuilder(
        context,
        PersonDb::class.java,
        "personDataBase")
        .allowMainThreadQueries().build()

    override suspend fun getPersons(): Flow<List<Person>> =
        db.getPersonDao().selectAll()

    override suspend fun removePerson(person: Person) {
        db.getPersonDao().delete(person)
    }

    override suspend fun addPerson(person: Person) {
        db.getPersonDao().insert(person)
    }
}
