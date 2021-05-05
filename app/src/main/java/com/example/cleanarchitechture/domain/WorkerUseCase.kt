package com.example.cleanarchitechture.domain

interface WorkerUseCase {
    fun addPersonRequest(name: String, rate: Float)
    fun getPersonsRequest()
}