package com.example.cleanarchitechture.server

import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.domain.SimplifyPersonRepository
import com.example.cleanarchitechture.entity.Person
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CloudSource: SimplifyPersonRepository {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PERSONS_SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    val apiService: APIService = retrofit.create(APIService::class.java)

    override suspend fun getPersons(): List<Person> {
        var result:List<Person> = listOf()
        withContext(Dispatchers.IO) {
            val requestResult = apiService.getPersons()
            result=requestResult.body()!!
        }
        return result
    }

    override suspend fun addPerson(person: Person) {
        withContext(Dispatchers.IO){
            apiService.addPerson(person)
        }
    }
}