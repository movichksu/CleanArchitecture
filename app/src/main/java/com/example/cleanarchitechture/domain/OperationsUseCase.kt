package com.example.cleanarchitechture.domain

interface OperationsUseCase {

    suspend fun getOperations(): List<Operation>
    fun removeOperation(operation: Operation)

}