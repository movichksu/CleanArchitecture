package com.example.cleanarchitechture.domain

interface CalculateRepository {

    fun calculate(operation: Operation): Int

}