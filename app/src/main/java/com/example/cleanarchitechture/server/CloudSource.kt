package com.example.cleanarchitechture.server

import com.example.cleanarchitechture.BuildConfig
import com.example.cleanarchitechture.Constants
import com.example.cleanarchitechture.domain.SimplifyPersonRepository
import com.example.cleanarchitechture.entity.Person
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CloudSource : SimplifyPersonRepository {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PERSONS_SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    val apiService: APIService = retrofit.create(APIService::class.java)

    override suspend fun getPersons(): List<Person> {
        var result: List<Person> = listOf()
        withContext(Dispatchers.IO) {
            val requestResult = apiService.getPersons()
            result = requestResult.body()!!
        }
        return result
    }

    override suspend fun addPerson(person: Person): Boolean {
        val response: Boolean
        withContext(Dispatchers.IO) {
            delay(5000)
            response = apiService.addPerson(person).isSuccessful
        }
        return response
    }
}