package com.example.cleanarchitechture.domain

import kotlinx.coroutines.flow.Flow

interface OperationsRepository {

    suspend fun getOperations(): Flow<List<Operation>>
    suspend fun addOperation(operation: Operation)
    suspend fun removeOperation(operation: Operation)

}