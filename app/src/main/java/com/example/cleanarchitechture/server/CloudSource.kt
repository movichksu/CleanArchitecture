package com.example.cleanarchitechture.server

import com.example.cleanarchitechture.domain.SimplifyPersonRepository
import com.example.cleanarchitechture.entity.Person
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CloudSource: SimplifyPersonRepository {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://eu-api.backendless.com/E49B1F96-2BBB-4579-833F-7D3F5E6C84F8/E7A8D6D8-229A-4BFC-9801-F3A60E597E3B/services/Person/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    val apiService: APIService = retrofit.create(APIService::class.java)

    override fun getPersons(): List<Person> {
        val requestResult = apiService.getPersons()
        return requestResult.execute().body()!!
    }

    override suspend fun addPerson(person: Person) {

    }
}