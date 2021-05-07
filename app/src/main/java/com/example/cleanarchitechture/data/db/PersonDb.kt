package com.example.cleanarchitechture.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cleanarchitechture.domain.entity.Person

@Database(entities = [Person::class], version = 1)
abstract class PersonDb : RoomDatabase() {

    abstract fun getPersonDao(): PersonDao
}
