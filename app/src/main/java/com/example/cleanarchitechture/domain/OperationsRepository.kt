package com.example.cleanarchitechture.domain

interface OperationsRepository {

    fun getOperations(): List<Operation>
    fun addOperation(operation: Operation)
//    fun removeOperation(position: Int)

}