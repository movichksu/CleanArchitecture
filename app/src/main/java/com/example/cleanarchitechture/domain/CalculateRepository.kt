package com.example.cleanarchitechture.domain

interface CalculateRepository {

    suspend fun calculate(operation: Operation): Int

}