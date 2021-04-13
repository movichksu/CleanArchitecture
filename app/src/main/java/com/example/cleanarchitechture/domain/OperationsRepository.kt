package com.example.cleanarchitechture.domain

interface OperationsRepository {

    suspend fun getOperations(): List<Operation>
    fun addOperation(operation: Operation)
    fun removeOperation(operation: Operation)

}