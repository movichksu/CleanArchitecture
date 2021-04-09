package com.example.cleanarchitechture.domain

interface CalculateUseCase {

    suspend fun calculate(first: Int, second: Int): Int

}