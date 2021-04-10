package com.example.cleanarchitechture.domain

interface OperationsUseCase {

    fun getOperations(): List<Operation>
    fun removeOperation(operation: Operation)

}