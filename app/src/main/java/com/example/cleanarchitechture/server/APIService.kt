package com.example.cleanarchitechture.server

import com.example.cleanarchitechture.entity.Person
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

        @GET("getPersons")
        fun getPersons(): Call<List<Person>>
        //fun addPerson()
}