package com.example.cleanarchitechture.domain

import kotlinx.coroutines.flow.Flow
import java.text.FieldPosition

class OperationsUseCaseImpl(
        private val operationsRepository: OperationsRepository
) : OperationsUseCase {

    override suspend fun getOperations(): Flow<List<Operation>> {
        return operationsRepository.getOperations()
    }

    override suspend fun removeOperation(operation: Operation) {
        operationsRepository.removeOperation(operation)
    }
}