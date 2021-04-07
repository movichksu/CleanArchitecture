package com.example.cleanarchitechture.domain

class OperationsUseCaseImpl (
    val operationsRepository: OperationsRepository
): OperationsUseCase {
    override fun getOperations(): List<Operation> {
        return operationsRepository.getOperations()
    }
}