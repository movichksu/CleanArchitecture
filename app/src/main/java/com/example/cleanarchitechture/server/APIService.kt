package com.example.cleanarchitechture.server

import com.example.cleanarchitechture.entity.Person
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

        @GET("getPersons")
        fun getPersons(): Call<List<Person>>

        @POST("addPerson")
        suspend fun addPerson(@Body person: Person): Response<Person>
}