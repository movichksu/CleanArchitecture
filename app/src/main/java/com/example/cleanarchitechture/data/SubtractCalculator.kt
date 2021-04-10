package com.example.cleanarchitechture.data

import com.example.cleanarchitechture.domain.CalculateRepository
import com.example.cleanarchitechture.domain.Operation

class SubtractCalculator : CalculateRepository {
    override suspend fun calculate(operation: Operation): Int {
        return operation.first - operation.second
    }
}