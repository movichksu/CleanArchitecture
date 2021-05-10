package com.example.cleanarchitechture.data.server

import com.example.cleanarchitechture.domain.entity.Person
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

        @GET("getPersons")
        suspend fun getPersons(): Response<List<Person>>

        @POST("addPerson")
        suspend fun addPerson(@Body person: Person): Response<Person>
}