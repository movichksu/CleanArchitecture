package com.example.cleanarchitechture.domain

import java.text.FieldPosition

class OperationsUseCaseImpl (
    val operationsRepository: OperationsRepository
): OperationsUseCase {

    override fun getOperations(): List<Operation> {
        return operationsRepository.getOperations()
    }

//    override fun removeOperation(position: Int){
//        operationsRepository.removeOperation(position)
//    }
}