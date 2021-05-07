package com.example.cleanarchitechture.domain

interface PersonWorkExecutor {
    fun addPersonRequest(name: String, rate: Float)
    fun getPersonsRequest()
}