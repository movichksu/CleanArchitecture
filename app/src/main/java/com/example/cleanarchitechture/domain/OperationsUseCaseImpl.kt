package com.example.cleanarchitechture.domain

import java.text.FieldPosition

class OperationsUseCaseImpl(
        private val operationsRepository: OperationsRepository
) : OperationsUseCase {

    override suspend fun getOperations(): List<Operation> {
        return operationsRepository.getOperations()
    }

    override fun removeOperation(operation: Operation) {
        operationsRepository.removeOperation(operation)
    }
}