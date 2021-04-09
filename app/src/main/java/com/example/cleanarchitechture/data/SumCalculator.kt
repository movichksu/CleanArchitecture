package com.example.cleanarchitechture.data

import com.example.cleanarchitechture.domain.CalculateRepository
import com.example.cleanarchitechture.domain.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class SumCalculator : CalculateRepository {

    override suspend fun calculate(operation: Operation): Int {
        //delay(5000)
        withContext(Dispatchers.IO) {
            var sum = 0
            for (i in 0..Int.MAX_VALUE) {
                if (sum % 2 == 0) {
                    sum += i
                } else {
                    sum -= i
                }
            }
        }
        return operation.first + operation.second
    }
}